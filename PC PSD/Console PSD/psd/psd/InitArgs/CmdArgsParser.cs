using System;
using System.Linq;
using PsdBasesSetter.Repositories.Objects;

namespace psd.InitArgs
{
    static class CmdArgsParser
    {

        public static bool Parse(Args args, string[] strArgs)
        {

            var success = ParseCmdType(strArgs, args);

            args.PcPath = GetArgVal(strArgs, "-b");
            args.PhonePath = GetArgVal(strArgs, "-p");
            args.UsePsd = strArgs.Contains("--usepsd");
            args.Help = strArgs.Contains("--help");
            
            args.PassItem = ParsePassItem(strArgs);

            if (!success || args.Help)
            {
                PrintHelp();
                return false;
            }
            return true;
        }


        private static bool ParseCmdType(string[] strArgs, Args args)
        {
            switch (strArgs[0])
            {
                case "list":
                    args.CmdType = CommandType.ListPasses;
                    break;
                case "add":
                    args.CmdType = CommandType.AddPass;
                    break;
                case "rem":
                    args.CmdType = CommandType.RemovePass;
                    break;
                case "edit":
                    args.CmdType = CommandType.EditPass;
                    break;
                case "info":
                    args.CmdType = CommandType.ShowPassInfo;
                    break;
                default:
                    return false;
            }
            return true;
        }


        private static PassItem ParsePassItem(string[] strArgs)
        {
            var title = GetArgVal(strArgs, "--title");
            var password = GetArgVal(strArgs, "--password");
            if (title == null || password == null)
                return null;
            var passItem = new PassItem(title, password);

            ushort parsedId;
            ushort.TryParse(GetArgVal(strArgs, "--id"), out parsedId);
            passItem.Id = parsedId;

            passItem.Login = GetArgVal(strArgs, "--login");

            bool enterWithLogin;
            bool.TryParse(GetArgVal(strArgs, "--enter-with-login"), out enterWithLogin);
            passItem.EnterWithLogin = enterWithLogin;

            passItem.Description = GetArgVal(strArgs, "--description");

            return passItem;
        }

        private static String GetArgVal(string[] strArgs, String argKey)
        {
            int indexOfArgValue = Array.IndexOf(strArgs, argKey) + 1;
            if (indexOfArgValue == 0)
                return null;
            return strArgs.ElementAtOrDefault(indexOfArgValue);
        }

        private static String GetArgVal(string[] strArgs, String argKey, String longKey)
        {
            String result;
            result = GetArgVal(strArgs, argKey);
            if (result == null)
                result = GetArgVal(strArgs, longKey);
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
}
