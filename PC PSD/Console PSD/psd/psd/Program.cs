using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PsdBasesSetter;
using PsdBasesSetter.Repositories.Objects;

namespace psd
{
    class Program
    {
        static void Main(string[] args)
        {
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
