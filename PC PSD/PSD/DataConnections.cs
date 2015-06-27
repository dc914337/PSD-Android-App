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
            return UpdateIfConnected(PcBase) && UpdateIfConnected(PhoneBase) && UpdateIfConnected(PsdBase);
        }


        public bool UpdateIfConnected(IRepository repository)
        {
            return !(repository?.Connected == true && repository.WriteChanges() == WriteResult.Error);
        }
        
        //wrote later than registered changes
        public bool AllUpToDate(DateTime lastEdit)
        {
            return UpToDate(PcBase, lastEdit) && UpToDate(PhoneBase, lastEdit) && UpToDate(PsdBase, lastEdit);
        }

        public bool UpToDate(IRepository repository, DateTime lastEdit)
        {
            return !(PcBase?.Connected == true && PcBase?.LastUpdated >= lastEdit);
        }


    }
}
