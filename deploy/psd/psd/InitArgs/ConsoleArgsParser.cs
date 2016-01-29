using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace psd.InitArgs
{
    static class ConsoleArgsParser
    {
        public static bool Fill(Args args)
        {
            bool success = true;
            FillUserPassword(args);
            switch (args.CmdType)
            {
                case CommandType.ResetPsd:
                    break;
                case CommandType.ListPasses:
                case CommandType.AddPass:
                case CommandType.EditPass:
                case CommandType.RemovePass:
                case CommandType.ShowPassInfo:
                    FillPcPath(args);
                    break;
                default:
                    Console.WriteLine("Error with console args parser commant type enum");
                    break;
            }
            return success;
        }


        private static void FillPcPath(Args args)
        {
            if (args.PcPath != null)
                return;

            Console.WriteLine("Enter path to PC base:");
            args.PcPath = Console.ReadLine();
        }

        private static void FillUserPassword(Args args)
        {
            if (args.UserPassword != null)
                return;
            Console.WriteLine("Enter user password:");
            args.UserPassword = Console.ReadLine();
        }
    }
}
