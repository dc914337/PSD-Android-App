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
            this.lstViewPasswords = new System.Windows.Forms.ListView();
            this.Id = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Title = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Login = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.lblBasePathDesc = new System.Windows.Forms.Label();
            this.lblAndroidPathDesc = new System.Windows.Forms.Label();
            this.lblPsdConnectionDesc = new System.Windows.Forms.Label();
            this.lblBasePath = new System.Windows.Forms.Label();
            this.lblAndroidBasePath = new System.Windows.Forms.Label();
            this.lblPsdComPort = new System.Windows.Forms.Label();
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
            this.connectToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
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
            // lblBasePathDesc
            // 
            this.lblBasePathDesc.AutoSize = true;
            this.lblBasePathDesc.Location = new System.Drawing.Point(12, 445);
            this.lblBasePathDesc.Name = "lblBasePathDesc";
            this.lblBasePathDesc.Size = new System.Drawing.Size(77, 13);
            this.lblBasePathDesc.TabIndex = 19;
            this.lblBasePathDesc.Text = "PC base path: ";
            this.lblBasePathDesc.Visible = false;
            // 
            // lblAndroidPathDesc
            // 
            this.lblAndroidPathDesc.AutoSize = true;
            this.lblAndroidPathDesc.Location = new System.Drawing.Point(12, 460);
            this.lblAndroidPathDesc.Name = "lblAndroidPathDesc";
            this.lblAndroidPathDesc.Size = new System.Drawing.Size(73, 13);
            this.lblAndroidPathDesc.TabIndex = 21;
            this.lblAndroidPathDesc.Text = "Android path: ";
            this.lblAndroidPathDesc.Visible = false;
            // 
            // lblPsdConnectionDesc
            // 
            this.lblPsdConnectionDesc.AutoSize = true;
            this.lblPsdConnectionDesc.Location = new System.Drawing.Point(12, 476);
            this.lblPsdConnectionDesc.Name = "lblPsdConnectionDesc";
            this.lblPsdConnectionDesc.Size = new System.Drawing.Size(90, 13);
            this.lblPsdConnectionDesc.TabIndex = 22;
            this.lblPsdConnectionDesc.Text = "Connected PSD: ";
            this.lblPsdConnectionDesc.Visible = false;
            // 
            // lblBasePath
            // 
            this.lblBasePath.AutoSize = true;
            this.lblBasePath.Location = new System.Drawing.Point(108, 447);
            this.lblBasePath.Name = "lblBasePath";
            this.lblBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblBasePath.TabIndex = 23;
            // 
            // lblAndroidBasePath
            // 
            this.lblAndroidBasePath.AutoSize = true;
            this.lblAndroidBasePath.Location = new System.Drawing.Point(108, 460);
            this.lblAndroidBasePath.Name = "lblAndroidBasePath";
            this.lblAndroidBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblAndroidBasePath.TabIndex = 24;
            // 
            // lblPsdComPort
            // 
            this.lblPsdComPort.AutoSize = true;
            this.lblPsdComPort.Location = new System.Drawing.Point(108, 476);
            this.lblPsdComPort.Name = "lblPsdComPort";
            this.lblPsdComPort.Size = new System.Drawing.Size(0, 13);
            this.lblPsdComPort.TabIndex = 25;
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
            this.menuStrip1.Size = new System.Drawing.Size(398, 24);
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
            this.writeFirmwareToolStripMenuItem,
            this.connectToolStripMenuItem});
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
            // connectToolStripMenuItem
            // 
            this.connectToolStripMenuItem.Name = "connectToolStripMenuItem";
            this.connectToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.connectToolStripMenuItem.Text = "Connect";
            // 
            // PSDForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(398, 491);
            this.Controls.Add(this.button10);
            this.Controls.Add(this.button8);
            this.Controls.Add(this.button7);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.lblPsdComPort);
            this.Controls.Add(this.lblAndroidBasePath);
            this.Controls.Add(this.lblBasePath);
            this.Controls.Add(this.lblPsdConnectionDesc);
            this.Controls.Add(this.lblAndroidPathDesc);
            this.Controls.Add(this.lblBasePathDesc);
            this.Controls.Add(this.lstViewPasswords);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "PSDForm";
            this.Text = "PSD";
            this.Load += new System.EventHandler(this.PSDForm_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ListView lstViewPasswords;
        private System.Windows.Forms.ColumnHeader Id;
        private System.Windows.Forms.ColumnHeader Title;
        private System.Windows.Forms.ColumnHeader Login;
        private System.Windows.Forms.Label lblBasePathDesc;
        private System.Windows.Forms.Label lblAndroidPathDesc;
        private System.Windows.Forms.Label lblPsdConnectionDesc;
        private System.Windows.Forms.Label lblBasePath;
        private System.Windows.Forms.Label lblAndroidBasePath;
        private System.Windows.Forms.Label lblPsdComPort;
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
        private System.Windows.Forms.ToolStripMenuItem connectToolStripMenuItem;
    }
}

