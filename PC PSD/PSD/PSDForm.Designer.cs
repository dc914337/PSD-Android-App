namespace PSD
{
    partial class PSDForm
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
            this.label2 = new System.Windows.Forms.Label();
            this.lstViewPasswords = new System.Windows.Forms.ListView();
            this.Id = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Title = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Login = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txtTitle = new System.Windows.Forms.TextBox();
            this.txtLogin = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.cbxEnterWithLogin = new System.Windows.Forms.CheckBox();
            this.cbxShowPassword = new System.Windows.Forms.CheckBox();
            this.button1 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.lblBasePathDesc = new System.Windows.Forms.Label();
            this.button5 = new System.Windows.Forms.Button();
            this.lblAndroidPathDesc = new System.Windows.Forms.Label();
            this.lblPsdConnectionDesc = new System.Windows.Forms.Label();
            this.lblBasePath = new System.Windows.Forms.Label();
            this.lblAndroidBasePath = new System.Windows.Forms.Label();
            this.lblPsdComPort = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.txtDescription = new System.Windows.Forms.RichTextBox();
            this.txtPass = new System.Windows.Forms.TextBox();
            this.nudId = new System.Windows.Forms.NumericUpDown();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAllToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.pCToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newPCBaseToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.phoneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem2 = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.deviceToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.resetToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToPSDToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.writeFirmwareToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.button2 = new System.Windows.Forms.Button();
            this.button6 = new System.Windows.Forms.Button();
            this.button7 = new System.Windows.Forms.Button();
            this.button8 = new System.Windows.Forms.Button();
            this.button10 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.nudId)).BeginInit();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(728, 765);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(63, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Description:";
            // 
            // lstViewPasswords
            // 
            this.lstViewPasswords.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.Id,
            this.Title,
            this.Login});
            this.lstViewPasswords.FullRowSelect = true;
            this.lstViewPasswords.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstViewPasswords.Location = new System.Drawing.Point(12, 22);
            this.lstViewPasswords.Name = "lstViewPasswords";
            this.lstViewPasswords.Size = new System.Drawing.Size(338, 420);
            this.lstViewPasswords.TabIndex = 5;
            this.lstViewPasswords.UseCompatibleStateImageBehavior = false;
            this.lstViewPasswords.View = System.Windows.Forms.View.Details;
            this.lstViewPasswords.SelectedIndexChanged += new System.EventHandler(this.lstViewPasswords_SelectedIndexChanged);
            // 
            // Id
            // 
            this.Id.Text = "Id";
            this.Id.Width = 99;
            // 
            // Title
            // 
            this.Title.Text = "Title";
            this.Title.Width = 102;
            // 
            // Login
            // 
            this.Login.Text = "Login";
            this.Login.Width = 99;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(728, 618);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(30, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "Title:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(728, 644);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(36, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Login:";
            // 
            // txtTitle
            // 
            this.txtTitle.Location = new System.Drawing.Point(764, 615);
            this.txtTitle.Name = "txtTitle";
            this.txtTitle.Size = new System.Drawing.Size(439, 20);
            this.txtTitle.TabIndex = 9;
            this.txtTitle.TextChanged += new System.EventHandler(this.RepresentationChanged);
            // 
            // txtLogin
            // 
            this.txtLogin.Location = new System.Drawing.Point(764, 641);
            this.txtLogin.Name = "txtLogin";
            this.txtLogin.Size = new System.Drawing.Size(439, 20);
            this.txtLogin.TabIndex = 10;
            this.txtLogin.TextChanged += new System.EventHandler(this.RepresentationChanged);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(728, 693);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(53, 13);
            this.label5.TabIndex = 11;
            this.label5.Text = "PassItem:";
            // 
            // cbxEnterWithLogin
            // 
            this.cbxEnterWithLogin.AutoSize = true;
            this.cbxEnterWithLogin.Location = new System.Drawing.Point(731, 667);
            this.cbxEnterWithLogin.Name = "cbxEnterWithLogin";
            this.cbxEnterWithLogin.Size = new System.Drawing.Size(98, 17);
            this.cbxEnterWithLogin.TabIndex = 13;
            this.cbxEnterWithLogin.Text = "Enter with login";
            this.cbxEnterWithLogin.UseVisualStyleBackColor = true;
            // 
            // cbxShowPassword
            // 
            this.cbxShowPassword.AutoSize = true;
            this.cbxShowPassword.Location = new System.Drawing.Point(731, 745);
            this.cbxShowPassword.Name = "cbxShowPassword";
            this.cbxShowPassword.Size = new System.Drawing.Size(101, 17);
            this.cbxShowPassword.TabIndex = 14;
            this.cbxShowPassword.Text = "Show password";
            this.cbxShowPassword.UseVisualStyleBackColor = true;
            this.cbxShowPassword.CheckedChanged += new System.EventHandler(this.cbxShowPassword_CheckedChanged);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(731, 881);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(153, 23);
            this.button1.TabIndex = 15;
            this.button1.Text = "Add as a new password";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(731, 910);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(153, 23);
            this.button3.TabIndex = 17;
            this.button3.Text = "Remove selected passwords";
            this.button3.UseVisualStyleBackColor = true;
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(729, 1008);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(175, 23);
            this.button4.TabIndex = 18;
            this.button4.Text = "Update in all available bases";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // lblBasePathDesc
            // 
            this.lblBasePathDesc.AutoSize = true;
            this.lblBasePathDesc.Location = new System.Drawing.Point(729, 954);
            this.lblBasePathDesc.Name = "lblBasePathDesc";
            this.lblBasePathDesc.Size = new System.Drawing.Size(77, 13);
            this.lblBasePathDesc.TabIndex = 19;
            this.lblBasePathDesc.Text = "PC base path: ";
            this.lblBasePathDesc.Visible = false;
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(732, 852);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(152, 23);
            this.button5.TabIndex = 20;
            this.button5.Text = "Backup base";
            this.button5.UseVisualStyleBackColor = true;
            // 
            // lblAndroidPathDesc
            // 
            this.lblAndroidPathDesc.AutoSize = true;
            this.lblAndroidPathDesc.Location = new System.Drawing.Point(729, 969);
            this.lblAndroidPathDesc.Name = "lblAndroidPathDesc";
            this.lblAndroidPathDesc.Size = new System.Drawing.Size(73, 13);
            this.lblAndroidPathDesc.TabIndex = 21;
            this.lblAndroidPathDesc.Text = "Android path: ";
            this.lblAndroidPathDesc.Visible = false;
            // 
            // lblPsdConnectionDesc
            // 
            this.lblPsdConnectionDesc.AutoSize = true;
            this.lblPsdConnectionDesc.Location = new System.Drawing.Point(729, 985);
            this.lblPsdConnectionDesc.Name = "lblPsdConnectionDesc";
            this.lblPsdConnectionDesc.Size = new System.Drawing.Size(90, 13);
            this.lblPsdConnectionDesc.TabIndex = 22;
            this.lblPsdConnectionDesc.Text = "Connected PSD: ";
            this.lblPsdConnectionDesc.Visible = false;
            // 
            // lblBasePath
            // 
            this.lblBasePath.AutoSize = true;
            this.lblBasePath.Location = new System.Drawing.Point(825, 956);
            this.lblBasePath.Name = "lblBasePath";
            this.lblBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblBasePath.TabIndex = 23;
            // 
            // lblAndroidBasePath
            // 
            this.lblAndroidBasePath.AutoSize = true;
            this.lblAndroidBasePath.Location = new System.Drawing.Point(825, 969);
            this.lblAndroidBasePath.Name = "lblAndroidBasePath";
            this.lblAndroidBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblAndroidBasePath.TabIndex = 24;
            // 
            // lblPsdComPort
            // 
            this.lblPsdComPort.AutoSize = true;
            this.lblPsdComPort.Location = new System.Drawing.Point(825, 985);
            this.lblPsdComPort.Name = "lblPsdComPort";
            this.lblPsdComPort.Size = new System.Drawing.Size(0, 13);
            this.lblPsdComPort.TabIndex = 25;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(728, 592);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(19, 13);
            this.label9.TabIndex = 26;
            this.label9.Text = "Id:";
            // 
            // txtDescription
            // 
            this.txtDescription.EnableAutoDragDrop = true;
            this.txtDescription.Location = new System.Drawing.Point(731, 781);
            this.txtDescription.Name = "txtDescription";
            this.txtDescription.Size = new System.Drawing.Size(472, 65);
            this.txtDescription.TabIndex = 6;
            this.txtDescription.Text = "";
            // 
            // txtPass
            // 
            this.txtPass.Location = new System.Drawing.Point(781, 691);
            this.txtPass.MaxLength = 126;
            this.txtPass.Multiline = true;
            this.txtPass.Name = "txtPass";
            this.txtPass.PasswordChar = '*';
            this.txtPass.Size = new System.Drawing.Size(422, 48);
            this.txtPass.TabIndex = 28;
            // 
            // nudId
            // 
            this.nudId.Location = new System.Drawing.Point(764, 590);
            this.nudId.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.nudId.Name = "nudId";
            this.nudId.Size = new System.Drawing.Size(120, 20);
            this.nudId.TabIndex = 29;
            this.nudId.ValueChanged += new System.EventHandler(this.RepresentationChanged);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.pCToolStripMenuItem,
            this.phoneToolStripMenuItem,
            this.deviceToolStripMenuItem,
            this.aboutToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(399, 24);
            this.menuStrip1.TabIndex = 30;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.saveAllToolStripMenuItem,
            this.exitToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // saveAllToolStripMenuItem
            // 
            this.saveAllToolStripMenuItem.Name = "saveAllToolStripMenuItem";
            this.saveAllToolStripMenuItem.Size = new System.Drawing.Size(113, 22);
            this.saveAllToolStripMenuItem.Text = "Save all";
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(113, 22);
            this.exitToolStripMenuItem.Text = "Exit";
            // 
            // pCToolStripMenuItem
            // 
            this.pCToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newPCBaseToolStripMenuItem,
            this.saveToolStripMenuItem,
            this.saveToolStripMenuItem1,
            this.saveAsToolStripMenuItem});
            this.pCToolStripMenuItem.Name = "pCToolStripMenuItem";
            this.pCToolStripMenuItem.Size = new System.Drawing.Size(34, 20);
            this.pCToolStripMenuItem.Text = "PC";
            // 
            // newPCBaseToolStripMenuItem
            // 
            this.newPCBaseToolStripMenuItem.Name = "newPCBaseToolStripMenuItem";
            this.newPCBaseToolStripMenuItem.Size = new System.Drawing.Size(114, 22);
            this.newPCBaseToolStripMenuItem.Text = "New";
            this.newPCBaseToolStripMenuItem.Click += new System.EventHandler(this.newPCBaseToolStripMenuItem_Click);
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(114, 22);
            this.saveToolStripMenuItem.Text = "Open";
            // 
            // saveToolStripMenuItem1
            // 
            this.saveToolStripMenuItem1.Name = "saveToolStripMenuItem1";
            this.saveToolStripMenuItem1.Size = new System.Drawing.Size(114, 22);
            this.saveToolStripMenuItem1.Text = "Save";
            // 
            // saveAsToolStripMenuItem
            // 
            this.saveAsToolStripMenuItem.Name = "saveAsToolStripMenuItem";
            this.saveAsToolStripMenuItem.Size = new System.Drawing.Size(114, 22);
            this.saveAsToolStripMenuItem.Text = "Save As";
            // 
            // phoneToolStripMenuItem
            // 
            this.phoneToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newToolStripMenuItem,
            this.openToolStripMenuItem,
            this.saveToolStripMenuItem2,
            this.saveAsToolStripMenuItem1});
            this.phoneToolStripMenuItem.Name = "phoneToolStripMenuItem";
            this.phoneToolStripMenuItem.Size = new System.Drawing.Size(53, 20);
            this.phoneToolStripMenuItem.Text = "Phone";
            // 
            // newToolStripMenuItem
            // 
            this.newToolStripMenuItem.Name = "newToolStripMenuItem";
            this.newToolStripMenuItem.Size = new System.Drawing.Size(114, 22);
            this.newToolStripMenuItem.Text = "New";
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(114, 22);
            this.openToolStripMenuItem.Text = "Open";
            // 
            // saveToolStripMenuItem2
            // 
            this.saveToolStripMenuItem2.Name = "saveToolStripMenuItem2";
            this.saveToolStripMenuItem2.Size = new System.Drawing.Size(114, 22);
            this.saveToolStripMenuItem2.Text = "Save";
            // 
            // saveAsToolStripMenuItem1
            // 
            this.saveAsToolStripMenuItem1.Name = "saveAsToolStripMenuItem1";
            this.saveAsToolStripMenuItem1.Size = new System.Drawing.Size(114, 22);
            this.saveAsToolStripMenuItem1.Text = "Save As";
            // 
            // deviceToolStripMenuItem
            // 
            this.deviceToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.resetToolStripMenuItem,
            this.saveToPSDToolStripMenuItem,
            this.writeFirmwareToolStripMenuItem});
            this.deviceToolStripMenuItem.Name = "deviceToolStripMenuItem";
            this.deviceToolStripMenuItem.Size = new System.Drawing.Size(54, 20);
            this.deviceToolStripMenuItem.Text = "Device";
            // 
            // resetToolStripMenuItem
            // 
            this.resetToolStripMenuItem.Name = "resetToolStripMenuItem";
            this.resetToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.resetToolStripMenuItem.Text = "Reset PSD";
            // 
            // saveToPSDToolStripMenuItem
            // 
            this.saveToPSDToolStripMenuItem.Name = "saveToPSDToolStripMenuItem";
            this.saveToPSDToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.saveToPSDToolStripMenuItem.Text = "Save to PSD";
            // 
            // writeFirmwareToolStripMenuItem
            // 
            this.writeFirmwareToolStripMenuItem.Name = "writeFirmwareToolStripMenuItem";
            this.writeFirmwareToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.writeFirmwareToolStripMenuItem.Text = "Write firmware";
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(52, 20);
            this.aboutToolStripMenuItem.Text = "About";
            // 
            // button2
            // 
            this.button2.Image = global::PSD.Properties.Resources.appbar_chevron_down;
            this.button2.Location = new System.Drawing.Point(351, 56);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(35, 35);
            this.button2.TabIndex = 36;
            this.button2.Text = " ";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // button6
            // 
            this.button6.Image = global::PSD.Properties.Resources.appbar_chevron_up;
            this.button6.Location = new System.Drawing.Point(351, 22);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(35, 35);
            this.button6.TabIndex = 37;
            this.button6.Text = " ";
            this.button6.UseVisualStyleBackColor = true;
            // 
            // button7
            // 
            this.button7.Image = global::PSD.Properties.Resources.appbar_save;
            this.button7.Location = new System.Drawing.Point(351, 397);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(45, 45);
            this.button7.TabIndex = 38;
            this.button7.Text = " ";
            this.button7.UseVisualStyleBackColor = true;
            // 
            // button8
            // 
            this.button8.Image = global::PSD.Properties.Resources.appbar_list_delete_inline;
            this.button8.Location = new System.Drawing.Point(351, 353);
            this.button8.Name = "button8";
            this.button8.Size = new System.Drawing.Size(45, 45);
            this.button8.TabIndex = 39;
            this.button8.Text = " ";
            this.button8.UseVisualStyleBackColor = true;
            // 
            // button10
            // 
            this.button10.Image = global::PSD.Properties.Resources.appbar_list_add;
            this.button10.Location = new System.Drawing.Point(351, 309);
            this.button10.Name = "button10";
            this.button10.Size = new System.Drawing.Size(45, 45);
            this.button10.TabIndex = 41;
            this.button10.Text = " ";
            this.button10.UseVisualStyleBackColor = true;
            // 
            // PSDForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(399, 449);
            this.Controls.Add(this.button10);
            this.Controls.Add(this.button8);
            this.Controls.Add(this.button7);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.nudId);
            this.Controls.Add(this.txtPass);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.lblPsdComPort);
            this.Controls.Add(this.lblAndroidBasePath);
            this.Controls.Add(this.lblBasePath);
            this.Controls.Add(this.lblPsdConnectionDesc);
            this.Controls.Add(this.lblAndroidPathDesc);
            this.Controls.Add(this.button5);
            this.Controls.Add(this.lblBasePathDesc);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.cbxShowPassword);
            this.Controls.Add(this.cbxEnterWithLogin);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.txtLogin);
            this.Controls.Add(this.txtTitle);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.txtDescription);
            this.Controls.Add(this.lstViewPasswords);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "PSDForm";
            this.Text = "p";
            this.Load += new System.EventHandler(this.PSDForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.nudId)).EndInit();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ListView lstViewPasswords;
        private System.Windows.Forms.ColumnHeader Id;
        private System.Windows.Forms.ColumnHeader Title;
        private System.Windows.Forms.ColumnHeader Login;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtTitle;
        private System.Windows.Forms.TextBox txtLogin;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.CheckBox cbxEnterWithLogin;
        private System.Windows.Forms.CheckBox cbxShowPassword;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.Label lblBasePathDesc;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.Label lblAndroidPathDesc;
        private System.Windows.Forms.Label lblPsdConnectionDesc;
        private System.Windows.Forms.Label lblBasePath;
        private System.Windows.Forms.Label lblAndroidBasePath;
        private System.Windows.Forms.Label lblPsdComPort;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.RichTextBox txtDescription;
        private System.Windows.Forms.TextBox txtPass;
        private System.Windows.Forms.NumericUpDown nudId;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAllToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem pCToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newPCBaseToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem phoneToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem deviceToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem saveAsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem2;
        private System.Windows.Forms.ToolStripMenuItem saveAsToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem resetToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToPSDToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem writeFirmwareToolStripMenuItem;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.Button button7;
        private System.Windows.Forms.Button button8;
        private System.Windows.Forms.Button button10;
    }
}

