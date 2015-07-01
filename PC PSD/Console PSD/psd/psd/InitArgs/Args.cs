using System;
using PsdBasesSetter.Repositories.Objects;

namespace psd.InitArgs
{
    public class Args
    {
        public CommandType CmdType { get; set; }
        public String PcPath { get; set; }
        public String PhonePath { get; set; }
        public bool UsePsd { get; set; }
        public bool Help { get; set; }
        public String UserPassword { get; set; }

        public PassItem PassItem { get; set; }

        public ushort? FindPassById { get; set; }

        public bool Verbose { get; set; } = false;


        private bool _useFirstFoundPsd;
        public bool UseFirstFoundPsd
        {
            get
            {
                return _useFirstFoundPsd;
            }
            set
            {
                if (value)
                    UsePsd = true;
                _useFirstFoundPsd = value;
            }
        }
    }

    public enum CommandType
    {
        ListPasses,
        AddPass,
        RemovePass,
        EditPass,
        ShowPassInfo,
        Update
    }

}
