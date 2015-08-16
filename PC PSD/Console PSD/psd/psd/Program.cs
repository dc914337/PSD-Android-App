using System;
using System.Linq;
using System.Runtime.InteropServices;
using psd.InitArgs;
using PsdBasesSetter;
using PsdBasesSetter.Device.Hid;
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

            if (!CmdArgsParser.Parse(_args, args))
                return;

            if (!ConsoleArgsParser.Fill(_args))
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
            //debug 
            var v = _connetions?.PhoneBase?.Base?.HBTKey;
            if (v != null)
                Console.WriteLine("Phone base's BtKey first byte: {0}", _connetions.PhoneBase?.Base?.HBTKey[0].ToString());
            if (_connetions?.PsdBase?.Base?.HBTKey != null)
                Console.WriteLine("PSD base's BtKey first byte: {0}", _connetions?.PsdBase?.Base?.HBTKey[0].ToString());
        }

        private static bool Connect()
        {
            _connetions = new DataConnections
            {
                UserPass = _args.UserPassword
            };

            bool allConnected = true;

            if (_args.PcPath != null && !_connetions.TrySetPcBase(_args.PcPath))
                allConnected &= _connetions.TryCreateAndSetPcBase(_args.PcPath);

            if (_args.PhonePath != null && !_connetions.TrySetPhoneBase(_args.PhonePath))
                allConnected &= _connetions.TryCreateAndSetPhoneBase(_args.PhonePath);

            if (_args.UsePsd)
                allConnected &= ConnectPsd();


            return allConnected;
        }



        private static bool ConnectPsd()
        {
            var devices = new PSDFinder().FindConnectedPsds();
            if (devices.Length < 1)
            {
                Output("No PSDs found", OutputType.Error);
                return false;
            }

            var selectedPsd = SelectPsdDevice(devices);
            if (selectedPsd == null)
                return false;

            if (!_connetions.TrySetPsdBase(selectedPsd))
                return false;

            return true;
        }

        private static PSDDevice SelectPsdDevice(PSDDevice[] devices)
        {
            if (_args.UseFirstFoundPsd)
                return devices[0];
            if (devices.Length == 1)
                return devices[0];

            Console.WriteLine("Select psd: ");
            for (int i = 0; i < devices.Length; i++)
            {
                Console.WriteLine("Id: {0}. Device: {1}", i, devices[i]);
            }
            int selected;
            if (!int.TryParse(Console.ReadLine(), out selected) || selected > devices.Length - 1)
            {
                Output("Wrong device id.", OutputType.Error);
                return null;
            }
            return devices[selected];
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
                case CommandType.Update:
                    return true;//stub. it will be updated in the end of execution
                case CommandType.ResetPsd:
                    return ResetPsd();
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


        private static bool ResetPsd()
        {
            return _connetions.PsdBase.Reset();
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
            if (selectedPass == null)
                return false;
            selectedPass?.Copy(_args.PassItem);
            return true;
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
