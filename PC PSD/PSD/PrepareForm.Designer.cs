namespace PSD
{
    partial class PrepareForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnSelectStorageFile = new System.Windows.Forms.Button();
            this.btnSelectPhoneFile = new System.Windows.Forms.Button();
            this.btnConnectPsd = new System.Windows.Forms.Button();
            this.btnCreatePhoneFile = new System.Windows.Forms.Button();
            this.btnCreateStorageFile = new System.Windows.Forms.Button();
            this.lblPsd = new System.Windows.Forms.Label();
            this.txtPassword = new System.Windows.Forms.TextBox();
            this.lblUsrPass = new System.Windows.Forms.Label();
            this.btnStart = new System.Windows.Forms.Button();
            this.lblPsdConnection = new System.Windows.Forms.Label();
            this.lblAndroidPath = new System.Windows.Forms.Label();
            this.lblBasePath = new System.Windows.Forms.Label();
            this.btnSet = new System.Windows.Forms.Button();
            this.nudCom = new System.Windows.Forms.NumericUpDown();
            ((System.ComponentModel.ISupportInitialize)(this.nudCom)).BeginInit();
            this.SuspendLayout();
            // 
            // btnSelectStorageFile
            // 
            this.btnSelectStorageFile.Enabled = false;
            this.btnSelectStorageFile.Location = new System.Drawing.Point(79, 46);
            this.btnSelectStorageFile.Name = "btnSelectStorageFile";
            this.btnSelectStorageFile.Size = new System.Drawing.Size(136, 23);
            this.btnSelectStorageFile.TabIndex = 0;
            this.btnSelectStorageFile.Text = "Select storage file";
            this.btnSelectStorageFile.UseVisualStyleBackColor = true;
            this.btnSelectStorageFile.Click += new System.EventHandler(this.btnSelectStorageFile_Click);
            // 
            // btnSelectPhoneFile
            // 
            this.btnSelectPhoneFile.Enabled = false;
            this.btnSelectPhoneFile.Location = new System.Drawing.Point(79, 99);
            this.btnSelectPhoneFile.Name = "btnSelectPhoneFile";
            this.btnSelectPhoneFile.Size = new System.Drawing.Size(136, 23);
            this.btnSelectPhoneFile.TabIndex = 1;
            this.btnSelectPhoneFile.Text = "Select android file";
            this.btnSelectPhoneFile.UseVisualStyleBackColor = true;
            this.btnSelectPhoneFile.Click += new System.EventHandler(this.btnSelectPhoneFile_Click);
            // 
            // btnConnectPsd
            // 
            this.btnConnectPsd.Enabled = false;
            this.btnConnectPsd.Location = new System.Drawing.Point(278, 154);
            this.btnConnectPsd.Name = "btnConnectPsd";
            this.btnConnectPsd.Size = new System.Drawing.Size(72, 23);
            this.btnConnectPsd.TabIndex = 2;
            this.btnConnectPsd.Text = "Connect";
            this.btnConnectPsd.UseVisualStyleBackColor = true;
            this.btnConnectPsd.Click += new System.EventHandler(this.btnConnectPsd_Click);
            // 
            // btnCreatePhoneFile
            // 
            this.btnCreatePhoneFile.Enabled = false;
            this.btnCreatePhoneFile.Location = new System.Drawing.Point(221, 99);
            this.btnCreatePhoneFile.Name = "btnCreatePhoneFile";
            this.btnCreatePhoneFile.Size = new System.Drawing.Size(136, 23);
            this.btnCreatePhoneFile.TabIndex = 4;
            this.btnCreatePhoneFile.Text = "Create android file";
            this.btnCreatePhoneFile.UseVisualStyleBackColor = true;
            this.btnCreatePhoneFile.Click += new System.EventHandler(this.btnCreatePhoneFile_Click);
            // 
            // btnCreateStorageFile
            // 
            this.btnCreateStorageFile.Enabled = false;
            this.btnCreateStorageFile.Location = new System.Drawing.Point(221, 46);
            this.btnCreateStorageFile.Name = "btnCreateStorageFile";
            this.btnCreateStorageFile.Size = new System.Drawing.Size(136, 23);
            this.btnCreateStorageFile.TabIndex = 3;
            this.btnCreateStorageFile.Text = "Create storage file";
            this.btnCreateStorageFile.UseVisualStyleBackColor = true;
            this.btnCreateStorageFile.Click += new System.EventHandler(this.btnCreateStorageFile_Click);
            // 
            // lblPsd
            // 
            this.lblPsd.AutoSize = true;
            this.lblPsd.Location = new System.Drawing.Point(69, 159);
            this.lblPsd.Name = "lblPsd";
            this.lblPsd.Size = new System.Drawing.Size(59, 13);
            this.lblPsd.TabIndex = 5;
            this.lblPsd.Text = "PSD COM:";
            // 
            // txtPassword
            // 
            this.txtPassword.Location = new System.Drawing.Point(124, 6);
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.Size = new System.Drawing.Size(195, 20);
            this.txtPassword.TabIndex = 8;
            this.txtPassword.TextChanged += new System.EventHandler(this.txtPassword_TextChanged);
            // 
            // lblUsrPass
            // 
            this.lblUsrPass.AutoSize = true;
            this.lblUsrPass.Location = new System.Drawing.Point(42, 9);
            this.lblUsrPass.Name = "lblUsrPass";
            this.lblUsrPass.Size = new System.Drawing.Size(80, 13);
            this.lblUsrPass.TabIndex = 7;
            this.lblUsrPass.Text = "User password:";
            // 
            // btnStart
            // 
            this.btnStart.Enabled = false;
            this.btnStart.Location = new System.Drawing.Point(170, 212);
            this.btnStart.Name = "btnStart";
            this.btnStart.Size = new System.Drawing.Size(75, 23);
            this.btnStart.TabIndex = 9;
            this.btnStart.Text = "Start!";
            this.btnStart.UseVisualStyleBackColor = true;
            this.btnStart.Click += new System.EventHandler(this.btnStart_Click);
            // 
            // lblPsdConnection
            // 
            this.lblPsdConnection.AutoSize = true;
            this.lblPsdConnection.Location = new System.Drawing.Point(134, 179);
            this.lblPsdConnection.Name = "lblPsdConnection";
            this.lblPsdConnection.Size = new System.Drawing.Size(0, 13);
            this.lblPsdConnection.TabIndex = 25;
            // 
            // lblAndroidPath
            // 
            this.lblAndroidPath.AutoSize = true;
            this.lblAndroidPath.Location = new System.Drawing.Point(69, 125);
            this.lblAndroidPath.Name = "lblAndroidPath";
            this.lblAndroidPath.Size = new System.Drawing.Size(0, 13);
            this.lblAndroidPath.TabIndex = 24;
            // 
            // lblBasePath
            // 
            this.lblBasePath.AutoSize = true;
            this.lblBasePath.Location = new System.Drawing.Point(69, 72);
            this.lblBasePath.Name = "lblBasePath";
            this.lblBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblBasePath.TabIndex = 23;
            // 
            // btnSet
            // 
            this.btnSet.Location = new System.Drawing.Point(325, 4);
            this.btnSet.Name = "btnSet";
            this.btnSet.Size = new System.Drawing.Size(72, 23);
            this.btnSet.TabIndex = 26;
            this.btnSet.Text = "Set";
            this.btnSet.UseVisualStyleBackColor = true;
            this.btnSet.Click += new System.EventHandler(this.btnSet_Click);
            // 
            // nudCom
            // 
            this.nudCom.Enabled = false;
            this.nudCom.Location = new System.Drawing.Point(134, 154);
            this.nudCom.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.nudCom.Name = "nudCom";
            this.nudCom.Size = new System.Drawing.Size(138, 20);
            this.nudCom.TabIndex = 27;
            // 
            // PrepareForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(450, 247);
            this.Controls.Add(this.nudCom);
            this.Controls.Add(this.btnSet);
            this.Controls.Add(this.btnStart);
            this.Controls.Add(this.txtPassword);
            this.Controls.Add(this.lblUsrPass);
            this.Controls.Add(this.lblPsd);
            this.Controls.Add(this.btnCreatePhoneFile);
            this.Controls.Add(this.btnCreateStorageFile);
            this.Controls.Add(this.btnConnectPsd);
            this.Controls.Add(this.btnSelectPhoneFile);
            this.Controls.Add(this.btnSelectStorageFile);
            this.Controls.Add(this.lblPsdConnection);
            this.Controls.Add(this.lblAndroidPath);
            this.Controls.Add(this.lblBasePath);
            this.Name = "PrepareForm";
            this.Text = "Prepare";
            this.Load += new System.EventHandler(this.PrepareForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.nudCom)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnSelectStorageFile;
        private System.Windows.Forms.Button btnSelectPhoneFile;
        private System.Windows.Forms.Button btnConnectPsd;
        private System.Windows.Forms.Button btnCreatePhoneFile;
        private System.Windows.Forms.Button btnCreateStorageFile;
        private System.Windows.Forms.Label lblPsd;
        private System.Windows.Forms.TextBox txtPassword;
        private System.Windows.Forms.Label lblUsrPass;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.Label lblPsdConnection;
        private System.Windows.Forms.Label lblAndroidPath;
        private System.Windows.Forms.Label lblBasePath;
        private System.Windows.Forms.Button btnSet;
        private System.Windows.Forms.NumericUpDown nudCom;
    }
}