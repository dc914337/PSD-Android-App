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
            FillPcPath(args);
           

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
