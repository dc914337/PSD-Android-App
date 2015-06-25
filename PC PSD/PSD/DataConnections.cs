using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSD
{
    public class DataConnections
    {
        public FileRepository PcBase { get; }
        public FileRepository PhoneBase { get; }
        public PSDRepository PsdBase { get; }
        private BasePasswords UserPasses { get; set; }

        public DataConnections(BasePasswords passes)
        {
            UserPasses = passes;
            PcBase = new FileRepository(passes.UserPassword);
            PhoneBase = new FileRepository(passes.PhonePassword);
            PsdBase = new PSDRepository();
        }

        public bool UpdateInAllAvailableBases()
        {
            if (!string.IsNullOrWhiteSpace(PcBase.Path))
            {
                PcBase.WriteChanges();
            }
            if (!string.IsNullOrWhiteSpace(PhoneBase.Path))
            {

            }

            if (PsdBase.Connected)
            {

            }
            return true;
        }


    }
}
