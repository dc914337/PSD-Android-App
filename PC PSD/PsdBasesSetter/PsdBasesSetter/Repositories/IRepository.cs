using System;

namespace PsdBasesSetter.Repositories
{
    public interface IRepository
    {
        bool WriteChanges();
        

        DateTime LastUpdated { get; }
    }
}
