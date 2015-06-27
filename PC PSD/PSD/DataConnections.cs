using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PSD.Repositories;

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
            bool success = true;

            if (PcBase?.Connected == true && PcBase?.WriteChanges() == WriteResult.Error)
                success = false;

            if (PhoneBase?.Connected == true && PhoneBase?.WriteChanges() == WriteResult.Error)
                success = false;

            if (PsdBase?.Connected == true && PsdBase?.WriteChanges() == WriteResult.Error)
                success = false;


            return success;
        }


        //wrote later than registered changes
        public bool AllUpToDate(DateTime lastEdit)
        {
            bool success = true;
            if (PcBase?.Connected == true && PcBase?.LastUpdated >= lastEdit)
                success = false;

            if (PhoneBase?.Connected == true && PhoneBase?.LastUpdated >= lastEdit)
                success = false;

            if (PsdBase?.Connected == true && PsdBase?.LastUpdated >= lastEdit)
                success = false;

            return success;
        }

    }
}
