using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Gnu.Getopt;
using psd.InitArgs;
using PsdBasesSetter;
using PsdBasesSetter.Repositories.Objects;

namespace psd
{
    class Program
    {
        private static Args _args;
        static void Main(string[] args)
        {
            _args = new Args();
            var argsParseResult = CmdArgsParser.Parse(_args, args);

            if (argsParseResult == null || _args.Help)
                return;


            do
            {
                DataConnections connetions = new DataConnections();
                connetions.UserPass = "root";//1
                connetions.TrySetPcBase("base.psd");//2
                connetions.TrySetPhoneBase("phone.psd");//3
                                                        //4

                Random rnd = new Random();
                connetions.Passwords.Add(new PassItem((ushort)rnd.Next(0, 100), "2", "2", true, "2", "2"));
                connetions.Passwords.Add(new PassItem((ushort)rnd.Next(0, 100), "2", "2", true, "2", "2"));

                connetions.WriteAll();
            } while (!Console.KeyAvailable);

        }
    }
}
