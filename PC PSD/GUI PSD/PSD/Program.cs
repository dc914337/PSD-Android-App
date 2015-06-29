using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PSD
{
    static class Program
    {
        ///<summary>
        ///The main entry point for the application.
        ///</summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            var prepareForm = new PrepareForm();

            Application.Run(prepareForm);

            if (!prepareForm.StartApp)
                return;

            var resDataConnections = prepareForm.DataConnections;
            var mainForm = new PSDForm(resDataConnections);
            Application.Run(mainForm);
        }
    }
}
