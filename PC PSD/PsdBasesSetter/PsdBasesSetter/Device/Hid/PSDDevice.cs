using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;
using HidSharp;

namespace PsdBasesSetter.Device.Hid
{
    public class PSDDevice
    {
        private const int DefaultDataLength = 64;
        private const int MaxAuthPassLength = 32;
        private const int MaxKeyLength = 32;

        private const byte ReportId = 0x00;
        private const int MaxPart2KeyLength = 128;

        private const byte PasswordsStartPageNum = 8;

        private const int PageSize = 32;
        private const byte LineEnd = 0x00;

        private enum Packages
        {
            Authorize = 0,
            ReadEepromPage = 1,
            WriteEepromPage = 2,
            Reset = 3
        };

        private HidDevice _hidDevice;
        HidStream _hidStream;
        public PSDDevice(HidDevice hidDevice)
        {
            _hidDevice = hidDevice;
        }

        public bool Connect()
        {
            return _hidDevice.TryOpen(out _hidStream);
        }

        public bool Login(byte[] password)
        {
            if (password.Length > MaxAuthPassLength)
                return false;
            try
            {
                byte[] result = WritePackage(Packages.Authorize, password);
                return CheckResult(result);
            }
            catch (IOException ex)
            {
                //throw ex;//for debug
                return false;
            }
        }


        public bool Reset(byte[] newPassword)
        {
            if (newPassword.Length > MaxAuthPassLength)
                return false;
            byte[] data = new byte[MaxAuthPassLength];
            byte[] passBytes = newPassword;
            Array.Copy(passBytes, data, passBytes.Length);

            try
            {
                byte[] result = WritePackage(Packages.Reset, data);
                return CheckResult(result);
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public bool WriteKeys(byte[] btKey, byte[] hBtKey)
        {
            const byte btKeyPage = 1;
            const byte hBtKeyPage = 2;

            if (btKey.Length > MaxKeyLength ||
                hBtKey.Length > MaxKeyLength)
                return false;

            try
            {
                // btKey is located in page 1
                // HbtKey is located in page 2
                if (!WriteEepromPage(btKeyPage, btKey))
                {
                    return false;
                }

                if (!WriteEepromPage(hBtKeyPage, hBtKey))
                {
                    return false;
                }

                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        //returns count passwords wrote
        public int WritePasswords(List<byte[]> passwords)
        {
            var wrote = 0;
            var pagesPerPassword = (int)Math.Ceiling((double)MaxPart2KeyLength / PageSize);
            for (var passIndex = 0; passIndex < passwords.Count; passIndex++)
            {
                var password = passwords[passIndex];

                bool success = true;
                for (var currentPasswordPage = 0; currentPasswordPage < pagesPerPassword; currentPasswordPage++)
                {
                    byte pageNum = (byte)(PasswordsStartPageNum + pagesPerPassword * passIndex + currentPasswordPage);//it MUST be byte
                    try
                    {
                        byte[] currentPasswordPageBuff = new byte[PageSize];
                        Array.Copy(password, (int)(currentPasswordPage * PageSize), currentPasswordPageBuff, 0, PageSize);

                        if (!WriteEepromPage(pageNum, currentPasswordPageBuff))
                        {
                            success = false;
                            break;
                        }
                    }
                    catch (Exception ex)
                    {
                        success = false;
                        break;
                    }
                }
                if (success)
                    wrote++;
            }
            return wrote;
        }


        private bool WriteEepromPage(byte pageNum, byte[] pageData)
        {
            if (pageData.Length > PageSize)
            {
                throw new Exception("EEPROM page can store maximum 32 bytes");
            }

            try
            {
                byte[] buff = new byte[PageSize + 1]; //+1 because of pagenum in start
                Array.Copy(pageData, 0, buff, 1, PageSize);
                buff[0] = pageNum;

                byte[] result = WritePackage(Packages.WriteEepromPage, buff);

                return CheckResult(result);
            }
            catch (IOException ex)
            {
                return false;
            }
        }

        private byte[] WritePackage(Packages packageType, byte[] data)
        {
            byte[] buf = new byte[DefaultDataLength + 1]; // +1 due reportId at beginning
            buf[0] = ReportId; // ReportId is zero
            buf[1] = (byte)packageType;

            // Copy data array into buffer
            Array.Copy(data, 0, buf, 2, (int)Math.Min(data.Length, DefaultDataLength - 1));

            // We have to write all data at once from single buffer
            _hidStream.Write(buf);

            var bufRead = new byte[DefaultDataLength + 1];

            _hidStream.ReadTimeout = 30 * 1000; // 30 sec timeout
            var recievedCount = _hidStream.Read(bufRead);

            if (recievedCount != DefaultDataLength + 1) // +1 due reportId leading byte
                throw new IOException(String.Format("Expected {0} bytes, recieved {1}", DefaultDataLength, recievedCount));


            // Remove leading zero byte (reportId) - consider this is a junk info
            var bufResponse = new byte[DefaultDataLength];
            Array.Copy(bufRead, 1, bufResponse, 0, DefaultDataLength);

            return bufResponse;
        }

        //removes last byte
        private void SetLineEnd(byte[] line)
        {
            line[line.Length - 1] = LineEnd;
        }


        private bool CheckResult(byte[] result)
        {
            return result[0] == 0x01;
        }

        public override string ToString()
        {
            return String.Format("PSD VID: {0} PID: {1}", _hidDevice.VendorID, _hidDevice.ProductID);
        }

    }
}