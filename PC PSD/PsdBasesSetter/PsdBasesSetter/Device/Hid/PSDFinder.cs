using System.Configuration;
using System.Linq;
using HidSharp;
using PsdBasesSetter.Config;

namespace PsdBasesSetter.Device.Hid
{
    public class PSDFinder
    {
        private PSDConfigSection _configSection;

        HidDeviceLoader _loader = new HidDeviceLoader();
        public PSDFinder()
        {
            _configSection = ConfigurationManager.OpenExeConfiguration(
                ConfigurationUserLevel.None).GetSection("PSDConfigSection") as PSDConfigSection;

        }

        public PSDDevice[] FindConnectedPsds()
        {
            var psds = _loader.GetDevices().Where(
                    d => d.MaxInputReportLength == _configSection.InputReportLength &&
                    d.VendorID == _configSection.VID &&
                    d.ProductID == _configSection.PID);

            return psds.Select(a => new PSDDevice(a)).ToArray();
        }
    }
}
