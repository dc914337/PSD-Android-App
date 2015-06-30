using System;

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
    }

    public enum CommandType
    {
        ListPasses,
        AddPass,
        RemovePass,
        EditPass,
        ShowPassInfo
    }

}
