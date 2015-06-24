using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using HidSharp;

namespace PSD.Device.Hid
{
    public class PSDDevice
    {
        private const int DefaultDataLength = 64;
        private const int MaxAuthPassLength = 31;
        private const int MaxKeyLength = 32;

        private const int MaxPart2KeyLength = 126;
        private const int PageIndexSize = 2;

        private const int PageSize = 32;
        private const byte LineEnd = 0x00;

        private enum Packages
        {
            Authorize = 0,
            ReadEepromPage = 1,
            WriteEepromPage = 2,
            ChangePassword = 3
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

        public bool Login(String password)
        {
            if (password.Length > MaxAuthPassLength)
                return false;
            try
            {
                byte[] result = WritePackage(Packages.Authorize, GetBytesFromString(password));
                return CheckResult(result);
            }
            catch (IOException ex)
            {
                throw ex;//for debug
                //return false;
            }
        }


        public bool Reset(String newPassword)
        {
            if (newPassword.Length > MaxAuthPassLength)
                return false;
            byte[] data = new byte[MaxAuthPassLength + 1];//pass+ \0
            byte[] passBytes = GetBytesFromString(newPassword);
            Array.Copy(passBytes, data, passBytes.Length);

            SetLineEnd(data);
            try
            {
                byte[] result = WritePackage(Packages.ChangePassword, data);
                return CheckResult(result);
            }
            catch (Exception ex)
            {
                throw ex;//debug
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
                throw ex;//debug
                //return false;
            }
        }

        //returns count passwords wrote
        public int WritePasswords(List<byte[]> passwords)
        {
            var wrote = 0;
            var pagesPerPassword = (int)Math.Ceiling((double)(MaxPart2KeyLength + PageIndexSize) / PageSize);
            for (var passIndex = 0; passIndex < passwords.Count; passIndex++)
            {
                var password = passwords[passIndex];
                byte[] buff = new byte[MaxPart2KeyLength + PageIndexSize];
                Array.Copy(BitConverter.GetBytes(passIndex), 0, buff, 0, PageIndexSize);//copying 2 little bytes of pageCount to start of the buff
                Array.Copy(password, 0, buff, PageIndexSize, password.Length);//copying data with offset 2(PageIndexSize)

                bool success = true;
                for (var page = 0; page < pagesPerPassword; page++)
                {
                    byte pageNum = (byte)(pagesPerPassword * passIndex + page);//it MUST be byte
                    try
                    {
                        if (WriteEepromPage(pageNum, password)) continue;
                        success = false;
                        break;
                    }
                    catch (Exception ex)
                    {
                        throw ex;//debug
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
                throw ex;//debug
                return false;
            }
        }

        private byte[] WritePackage(Packages packageType, byte[] data)
        {
            _hidStream.WriteByte((byte)packageType);
            _hidStream.Write(data);
            var buf = new byte[DefaultDataLength];
            var recievedCount = _hidStream.Read(buf);
            if (recievedCount != DefaultDataLength)
                throw new IOException(String.Format("Expected {0} bytes, recieved {1}", DefaultDataLength, recievedCount));
            return buf;
        }

        //removes last byte
        private void SetLineEnd(byte[] line)
        {
            line[line.Length - 1] = LineEnd;
        }

        private static byte[] GetBytesFromString(String text)
        {
            return Encoding.ASCII.GetBytes(text);
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
