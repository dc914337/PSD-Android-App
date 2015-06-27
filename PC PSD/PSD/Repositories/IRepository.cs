using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSD.Repositories
{
    public interface IRepository
    {
        WriteResult WriteChanges();

        bool Connected { get; }

        DateTime LastUpdated { get; set; }
    }
}
