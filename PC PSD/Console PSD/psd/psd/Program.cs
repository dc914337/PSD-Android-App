using System;
using psd.InitArgs;
using PsdBasesSetter;
using PsdBasesSetter.Repositories.Objects;

namespace psd
{
    class Program
    {
        private static Args _args;
        private static DataConnections _connetions;


        static void Main(string[] args)
        {
            _args = new Args();

            var argsParseResult = CmdArgsParser.Parse(_args, args);
            argsParseResult &= ConsoleArgsParser.Fill(_args);

            if (!argsParseResult || _args.Help)
                return;

            if (Connect())
                Console.WriteLine("Connected.");
            else
                Console.WriteLine("Can't connect. Maybe your password is wrong");
            Console.ReadKey();
        }

        private static bool Connect()
        {
            _connetions = new DataConnections
            {
                UserPass = _args.UserPassword
            };
            var setPcResult = _connetions.TrySetPcBase(_args.PcPath);
            var setPhoneResult = _connetions.TrySetPhoneBase(_args.PhonePath);
            return setPhoneResult || setPcResult;
        }
    }
}
