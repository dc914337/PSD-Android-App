using System;
using System.Linq;

namespace psd.CmdArgs
{
    public class Args
    {
        public CommandType CmdType { get; private set; }
        public String PcPath { get; private set; }
        public String PhonePath { get; private set; }
        public bool UsePsd { get; private set; }
        public bool Help { get; private set; }

        private string[] _args;
        public Args(string[] args)
        {
            _args = args;
        }

        public bool Parse()
        {
            bool success = true;
            success &= ParseCmdType();
            PcPath = GetArgVal("-b");
            PhonePath = GetArgVal("-p");
            UsePsd = _args.Contains("--usepsd");
            Help = _args.Contains("--help");

            if (!success || Help)
                PrintHelp();
            return success;
        }

        private bool ParseCmdType()
        {
            switch (_args[0])
            {
                case "list":
                    CmdType = CommandType.ListPasses;
                    break;
                case "add":
                    CmdType = CommandType.AddPass;
                    break;
                case "rem":
                    CmdType = CommandType.RemovePass;
                    break;
                case "edit":
                    CmdType = CommandType.EditPass;
                    break;
                case "info":
                    CmdType = CommandType.ShowPassInfo;
                    break;
                default:
                    return false;
            }
            return true;
        }

        private String GetArgVal(String argKey)
        {
            int indexOfArgValue = Array.IndexOf(_args, argKey) + 1;
            if (indexOfArgValue == 0)
                return null;
            return _args.ElementAtOrDefault(indexOfArgValue);
        }

        private String GetArgVal(String argKey, String longKey)
        {
            String result;
            result = GetArgVal(argKey);
            if (result == null)
                result = GetArgVal(longKey);
            return result;
        }

        public static void PrintHelp()
        {
            Console.WriteLine("Help: ");
            Console.WriteLine("Commands: ");
            Console.WriteLine("list");
            Console.WriteLine("add");
            Console.WriteLine("rem");
            Console.WriteLine("edit");
            Console.WriteLine("info");
            Console.WriteLine("Args: ");
            Console.WriteLine("-b - PC base path");
            Console.WriteLine("-p - Phone base path");
            Console.WriteLine("--usepsd - Find and conect PSD base");
        }
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
