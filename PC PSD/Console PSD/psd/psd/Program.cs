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
                case CommandType.ShowPassInfo:
                    return ShowPass();
                case CommandType.EditPass:
                    return EditPass();
                case CommandType.RemovePass:
                    return RemovePass();
                default:
                    Output("No such command. Magic enum", OutputType.Error);
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

        private static PassItem GetSelectedItemId()
        {
            if (!_args.FindPassById.HasValue)
            {
                Output("Need index to show pass", OutputType.Error);
                return null;
            }
            var selectedPass = _connetions.Passwords.GetPassById(_args.FindPassById.Value);
            if (selectedPass == null)
            {
                Output("No pass with such id", OutputType.Error);
                return null;
            }
            return selectedPass;
        }



        private static bool RemovePass()
        {
            var selectedPass = GetSelectedItemId();
            if (selectedPass == null)
                return false;

            return _connetions.Passwords.RemovePass(selectedPass.Id.Value);
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
            var selectedPass = GetSelectedItemId();
            selectedPass?.Copy(_args.PassItem);
            return false;
        }

        private static bool ShowPass()
        {
            var selectedPass = GetSelectedItemId();
            if (selectedPass == null)
                return false;

            Console.WriteLine("___[ Password info. Id: {0} ]___", selectedPass.Id);
            Console.WriteLine("Title: {0}", selectedPass.Title);
            Console.WriteLine("Login: {0}", selectedPass.Login);
            Console.WriteLine("Password: {0}", selectedPass.Pass);
            Console.WriteLine("Enter with login: {0}", selectedPass.EnterWithLogin);
            Console.WriteLine("Description: {0}", selectedPass.Description);
            return true;
        }

        private static void Output(String text, OutputType type)
        {
            if (type != OutputType.Verbose || _args.Verbose)
                Console.WriteLine(text);
        }

        enum OutputType
        {
            Error,
            Verbose
        }
    }
}
