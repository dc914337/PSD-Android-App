using System.Configuration;

namespace PSD.Config
{
    class PSDConfigSection : ConfigurationSection
    {
        [ConfigurationProperty("InputReportLength", IsRequired = true)]
        public int InputReportLength
        {
            get
            {
                return (int)this["InputReportLength"];
            }
            set
            {
                this["InputReportLength"] = value;
            }
        }

        [ConfigurationProperty("PID", IsRequired = true)]
        public int PID
        {
            get
            {
                return (int)this["PID"];
            }
            set
            {
                this["PID"] = value;
            }
        }

        [ConfigurationProperty("VID", IsRequired = true)]
        public int VID
        {
            get
            {
                return (int)this["VID"];
            }
            set
            {
                this["VID"] = value;
            }
        }
    }
}
