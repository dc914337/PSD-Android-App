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

            if (!Connect())
            {
                Console.WriteLine("Can't connect. Maybe your password is wrong");
                return;
            }
            Console.WriteLine("Connected");

            if (!ExecCommand())
            {
                Console.WriteLine("Executed with errors.");
                return;
            }
            Console.WriteLine("Executed");

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

        private static bool ExecCommand()
        {
            switch (_args.CmdType)
            {
                case CommandType.AddPass:
                    return AddPass();
                case CommandType.ListPasses:
                    return ListPasses();
                default:
                    return false;
            }
        }


        private static bool AddPass()
        {
            //_connetions.Passwords
            return false;
        }

        private static bool RemovePass()
        {
            return false;
        }

        private static bool ListPasses()
        {
            return false;
        }

        private static bool EditPass()
        {
            return false;
        }

        private static bool ShowPass()
        {
            return false;
        }

    }
}
