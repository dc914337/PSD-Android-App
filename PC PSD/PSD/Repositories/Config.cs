using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace PSD
{
    class Config
    {
        public string PhoneBasePath
        {
            get
            {
                return appSettings.AppSettings.Settings["PhoneBasePath"].Value;
            }
            set
            {
                appSettings.AppSettings.Settings["PhoneBasePath"].Value = value;
                appSettings.Save();
            }
        }
        public string PcBasePath
        {
            get
            {
                return appSettings.AppSettings.Settings["PcBasePath"].Value;
            }
            set
            {
                appSettings.AppSettings.Settings["PcBasePath"].Value = value;
                appSettings.Save();
            }
        }
        public byte PsdCom
        {
            get
            {
                byte value;
                if (byte.TryParse(appSettings.AppSettings.Settings["ComPort"].Value, out value))
                    return value;
                else
                    return 0;
            }
            set
            {
                appSettings.AppSettings.Settings["ComPort"].Value = value.ToString();
                appSettings.Save();
            }
        }

        private Configuration appSettings;
        public Config()
        {


            string appPath = System.IO.Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            string configFile = System.IO.Path.Combine(appPath, "PSD.exe.config");
            ExeConfigurationFileMap configFileMap = new ExeConfigurationFileMap();
            configFileMap.ExeConfigFilename = configFile;
            appSettings = ConfigurationManager.OpenMappedExeConfiguration(configFileMap, ConfigurationUserLevel.None);

        }

    }
}
