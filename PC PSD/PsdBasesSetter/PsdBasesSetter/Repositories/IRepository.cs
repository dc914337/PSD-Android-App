using System;

namespace PsdBasesSetter.Repositories
{
    public interface IRepository
    {
        SetResult WriteChanges();

        bool Connected { get; }

        DateTime LastUpdated { get; }
    }
}
