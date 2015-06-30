using System;
using System.Linq;
using System.Runtime.InteropServices;
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
                Output("Can't connect. Maybe your password is wrong", OutputType.Error);
                return;
            }
            Output("Connected", OutputType.Verbose);

            if (!ExecCommand())
            {
                Output("Executed with errors.", OutputType.Error);
                return;
            }
            _connetions.WriteAll();
            Output("Success!", OutputType.Verbose);

            
            Console.ReadKey();
        }

        private static bool Connect()
        {
            _connetions = new DataConnections
            {
                UserPass = _args.UserPassword
            };

            bool setPcResult = true;
            if (!_connetions.TrySetPcBase(_args.PcPath))
                setPcResult = _connetions.TryCreateAndSetPcBase(_args.PcPath);

            if (!_connetions.TrySetPhoneBase(_args.PhonePath))
                _connetions.TryCreateAndSetPhoneBase(_args.PhonePath);
            return setPcResult;
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
            if (_args.PassItem == null)
            {
                Output("No pass item set", OutputType.Error);
                return false;
            }
            return _connetions.Passwords.AddPass(_args.PassItem);
        }

        private static bool RemovePass()
        {
            return false;
        }

        private static bool ListPasses()
        {
            Console.WriteLine("Passwords: ");
            foreach (var pass in _connetions.Passwords.Select(a => a.Value))
            {
                Console.WriteLine("{0}:\t{1}\t{2}", pass.Id, pass.Title, pass.Description);
            }
            return true;
        }

        private static bool EditPass()
        {
            return false;
        }

        private static bool ShowPass()
        {

            return false;
        }

        private static void Output(String text, OutputType type)
        {
            if (type != OutputType.Verbose || !_args.Verbose)
                Console.WriteLine(text);
        }

        enum OutputType
        {
            Error,
            Verbose
        }
    }
}
