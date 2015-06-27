using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Security.Permissions;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;
using PSD.Annotations;
using PSD.Repositories;
using PSD.Repositories.Objects;
using PSD.Repositories.Serializers;

namespace PSD
{
    public class FileRepository : INotifyPropertyChanged, IRepository
    {

        public Base Base { get; set; }

        private byte[] EncryptionKey { get; set; }

        private string _path;
        public string Path
        {
            get
            {
                return _path;
            }
            set
            {
                _path = value;
                OnPropertyChanged();
            }
        }

        public bool Connected => Ready();

        public DateTime LastUpdated { get; set; }

        private bool Ready()
        {
            if (String.IsNullOrWhiteSpace(Path))
                return false;
            return true;
        }

        public FileRepository()
        {
            //EncryptionKey = null
        }
        public FileRepository(byte[] encryptionKey)
        {
            EncryptionKey = encryptionKey;
        }


        private bool MapData()
        {
            Base = new Base();
            byte[] dataBytes = ReadAllBytes();

            string dataStr;
            if (EncryptionKey != null)//decrypt
            {
                CryptoBase crypto = new CryptoBase(EncryptionKey);
                if (dataBytes.Length < CryptoBase.IVLength)
                    return false;
                if (!crypto.DecryptAll(dataBytes, out dataStr))
                    return false;
            }
            else
            {
                if (dataBytes.Length == 0)
                    return false;
                dataStr = Encoding.ASCII.GetString(dataBytes);
            }

            Base = Serializer.Deserialize(dataStr);
            return true;
        }



        private byte[] ReadAllBytes()
        {
            try
            {
                byte[] data;
                using (FileStream fsStream = new FileStream(Path, FileMode.Open))
                {
                    data = new byte[fsStream.Length];
                    fsStream.Read(data, 0, data.Length);
                }
                return data;
            }
            catch (Exception ex)
            {

                return new byte[0];
            }

        }


        public bool Create(string path)
        {
            if (string.IsNullOrWhiteSpace(path))
                return false;

            Path = path;
            Base = new Base();

            WriteChanges();
            return MapData();
        }


        public bool Connect(string path)
        {
            if (string.IsNullOrWhiteSpace(path))
                return false;

            Path = path;
            if (!MapData())
            {
                Path = null;
                return false;
            }

            WriteChanges();

            return true;
        }

        public event PropertyChangedEventHandler PropertyChanged;

        [NotifyPropertyChangedInvocator]
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public WriteResult WriteChanges()
        {
            CryptoBase crypto = new CryptoBase(EncryptionKey);

            string xmlToSerialize = Serializer.Serialize(Base);

            byte[] toWrite;

            if (EncryptionKey != null)
            {
                toWrite = crypto.EncryptAll(xmlToSerialize);
            }
            else
            {
                toWrite = Encoding.ASCII.GetBytes(xmlToSerialize);
            }


            try
            {
                using (FileStream fsStream = new FileStream(Path, FileMode.Create))
                {
                    fsStream.Write(toWrite, 0, toWrite.Length);
                }
            }
            catch (Exception ex)
            {
                return WriteResult.Error;
            }

            LastUpdated = DateTime.Now;
            return WriteResult.Success;
        }

    }
}
