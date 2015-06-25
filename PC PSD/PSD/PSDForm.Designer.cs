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
            this.Title = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Login = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.lblBasePathDesc = new System.Windows.Forms.Label();
            this.lblAndroidPathDesc = new System.Windows.Forms.Label();
            this.lblPsdConnectionDesc = new System.Windows.Forms.Label();
            this.lblBasePath = new System.Windows.Forms.Label();
            this.lblAndroidBasePath = new System.Windows.Forms.Label();
            this.lblPsdComPort = new System.Windows.Forms.Label();
            this.menuMain = new System.Windows.Forms.MenuStrip();
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
            this.connectToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.btnMoveDown = new System.Windows.Forms.Button();
            this.btnMoveUp = new System.Windows.Forms.Button();
            this.btnSaveAll = new System.Windows.Forms.Button();
            this.btnRemovePass = new System.Windows.Forms.Button();
            this.btnAddPass = new System.Windows.Forms.Button();
            this.btnUpdate = new System.Windows.Forms.Button();
            this.menuMain.SuspendLayout();
            this.SuspendLayout();
            // 
            // lstViewPasswords
            // 
            this.lstViewPasswords.Activation = System.Windows.Forms.ItemActivation.OneClick;
            this.lstViewPasswords.BackColor = System.Drawing.SystemColors.Window;
            this.lstViewPasswords.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.Title,
            this.Login});
            this.lstViewPasswords.FullRowSelect = true;
            this.lstViewPasswords.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstViewPasswords.HideSelection = false;
            this.lstViewPasswords.Location = new System.Drawing.Point(7, 27);
            this.lstViewPasswords.Name = "lstViewPasswords";
            this.lstViewPasswords.Size = new System.Drawing.Size(338, 420);
            this.lstViewPasswords.TabIndex = 5;
            this.lstViewPasswords.UseCompatibleStateImageBehavior = false;
            this.lstViewPasswords.View = System.Windows.Forms.View.Details;
            this.lstViewPasswords.DoubleClick += new System.EventHandler(this.lstViewPasswords_DoubleClick);
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
            this.lblBasePathDesc.Location = new System.Drawing.Point(4, 450);
            this.lblBasePathDesc.Name = "lblBasePathDesc";
            this.lblBasePathDesc.Size = new System.Drawing.Size(77, 13);
            this.lblBasePathDesc.TabIndex = 19;
            this.lblBasePathDesc.Text = "PC base path: ";
            this.lblBasePathDesc.Visible = false;
            // 
            // lblAndroidPathDesc
            // 
            this.lblAndroidPathDesc.AutoSize = true;
            this.lblAndroidPathDesc.Location = new System.Drawing.Point(4, 465);
            this.lblAndroidPathDesc.Name = "lblAndroidPathDesc";
            this.lblAndroidPathDesc.Size = new System.Drawing.Size(73, 13);
            this.lblAndroidPathDesc.TabIndex = 21;
            this.lblAndroidPathDesc.Text = "Android path: ";
            this.lblAndroidPathDesc.Visible = false;
            // 
            // lblPsdConnectionDesc
            // 
            this.lblPsdConnectionDesc.AutoSize = true;
            this.lblPsdConnectionDesc.Location = new System.Drawing.Point(4, 481);
            this.lblPsdConnectionDesc.Name = "lblPsdConnectionDesc";
            this.lblPsdConnectionDesc.Size = new System.Drawing.Size(90, 13);
            this.lblPsdConnectionDesc.TabIndex = 22;
            this.lblPsdConnectionDesc.Text = "Connected PSD: ";
            this.lblPsdConnectionDesc.Visible = false;
            // 
            // lblBasePath
            // 
            this.lblBasePath.AutoSize = true;
            this.lblBasePath.Location = new System.Drawing.Point(100, 452);
            this.lblBasePath.Name = "lblBasePath";
            this.lblBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblBasePath.TabIndex = 23;
            // 
            // lblAndroidBasePath
            // 
            this.lblAndroidBasePath.AutoSize = true;
            this.lblAndroidBasePath.Location = new System.Drawing.Point(100, 465);
            this.lblAndroidBasePath.Name = "lblAndroidBasePath";
            this.lblAndroidBasePath.Size = new System.Drawing.Size(0, 13);
            this.lblAndroidBasePath.TabIndex = 24;
            // 
            // lblPsdComPort
            // 
            this.lblPsdComPort.AutoSize = true;
            this.lblPsdComPort.Location = new System.Drawing.Point(100, 481);
            this.lblPsdComPort.Name = "lblPsdComPort";
            this.lblPsdComPort.Size = new System.Drawing.Size(0, 13);
            this.lblPsdComPort.TabIndex = 25;
            // 
            // menuMain
            // 
            this.menuMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.pCToolStripMenuItem,
            this.phoneToolStripMenuItem,
            this.deviceToolStripMenuItem,
            this.aboutToolStripMenuItem});
            this.menuMain.Location = new System.Drawing.Point(0, 0);
            this.menuMain.Name = "menuMain";
            this.menuMain.Size = new System.Drawing.Size(416, 24);
            this.menuMain.TabIndex = 30;
            this.menuMain.Text = "menuStrip1";
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
            // connectToolStripMenuItem
            // 
            this.connectToolStripMenuItem.Name = "connectToolStripMenuItem";
            this.connectToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.connectToolStripMenuItem.Text = "Connect";
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(52, 20);
            this.aboutToolStripMenuItem.Text = "About";
            // 
            // btnMoveDown
            // 
            this.btnMoveDown.Image = global::PSD.Properties.Resources.appbar_chevron_down;
            this.btnMoveDown.Location = new System.Drawing.Point(349, 61);
            this.btnMoveDown.Name = "btnMoveDown";
            this.btnMoveDown.Size = new System.Drawing.Size(35, 35);
            this.btnMoveDown.TabIndex = 36;
            this.btnMoveDown.Text = " ";
            this.btnMoveDown.UseVisualStyleBackColor = true;
            this.btnMoveDown.Click += new System.EventHandler(this.btnMoveDown_Click);
            // 
            // btnMoveUp
            // 
            this.btnMoveUp.Image = global::PSD.Properties.Resources.appbar_chevron_up;
            this.btnMoveUp.Location = new System.Drawing.Point(349, 27);
            this.btnMoveUp.Name = "btnMoveUp";
            this.btnMoveUp.Size = new System.Drawing.Size(35, 35);
            this.btnMoveUp.TabIndex = 37;
            this.btnMoveUp.Text = " ";
            this.btnMoveUp.UseVisualStyleBackColor = true;
            this.btnMoveUp.Click += new System.EventHandler(this.btnMoveUp_Click);
            // 
            // btnSaveAll
            // 
            this.btnSaveAll.Image = global::PSD.Properties.Resources.appbar_save;
            this.btnSaveAll.Location = new System.Drawing.Point(349, 392);
            this.btnSaveAll.Name = "btnSaveAll";
            this.btnSaveAll.Size = new System.Drawing.Size(55, 55);
            this.btnSaveAll.TabIndex = 38;
            this.btnSaveAll.Text = " ";
            this.btnSaveAll.UseVisualStyleBackColor = true;
            this.btnSaveAll.Click += new System.EventHandler(this.btnSaveAll_Click);
            // 
            // btnRemovePass
            // 
            this.btnRemovePass.Image = global::PSD.Properties.Resources.appbar_list_delete_inline;
            this.btnRemovePass.Location = new System.Drawing.Point(349, 335);
            this.btnRemovePass.Name = "btnRemovePass";
            this.btnRemovePass.Size = new System.Drawing.Size(55, 55);
            this.btnRemovePass.TabIndex = 39;
            this.btnRemovePass.Text = " ";
            this.btnRemovePass.UseVisualStyleBackColor = true;
            this.btnRemovePass.Click += new System.EventHandler(this.btnRemovePass_Click);
            // 
            // btnAddPass
            // 
            this.btnAddPass.Image = global::PSD.Properties.Resources.appbar_list_add_below;
            this.btnAddPass.Location = new System.Drawing.Point(349, 278);
            this.btnAddPass.Name = "btnAddPass";
            this.btnAddPass.Size = new System.Drawing.Size(55, 55);
            this.btnAddPass.TabIndex = 41;
            this.btnAddPass.Text = " ";
            this.btnAddPass.UseVisualStyleBackColor = true;
            this.btnAddPass.Click += new System.EventHandler(this.btnAddPass_Click);
            // 
            // btnUpdate
            // 
            this.btnUpdate.Image = global::PSD.Properties.Resources.appbar_refresh;
            this.btnUpdate.Location = new System.Drawing.Point(349, 217);
            this.btnUpdate.Name = "btnUpdate";
            this.btnUpdate.Size = new System.Drawing.Size(55, 55);
            this.btnUpdate.TabIndex = 42;
            this.btnUpdate.Text = " ";
            this.btnUpdate.UseVisualStyleBackColor = true;
            this.btnUpdate.Click += new System.EventHandler(this.btnUpdate_Click);
            // 
            // PSDForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(416, 500);
            this.Controls.Add(this.btnUpdate);
            this.Controls.Add(this.btnAddPass);
            this.Controls.Add(this.btnRemovePass);
            this.Controls.Add(this.btnSaveAll);
            this.Controls.Add(this.btnMoveUp);
            this.Controls.Add(this.btnMoveDown);
            this.Controls.Add(this.lblPsdComPort);
            this.Controls.Add(this.lblAndroidBasePath);
            this.Controls.Add(this.lblBasePath);
            this.Controls.Add(this.lblPsdConnectionDesc);
            this.Controls.Add(this.lblAndroidPathDesc);
            this.Controls.Add(this.lblBasePathDesc);
            this.Controls.Add(this.lstViewPasswords);
            this.Controls.Add(this.menuMain);
            this.MainMenuStrip = this.menuMain;
            this.Name = "PSDForm";
            this.Text = "PSD";
            this.Load += new System.EventHandler(this.PSDForm_Load);
            this.menuMain.ResumeLayout(false);
            this.menuMain.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ListView lstViewPasswords;
        private System.Windows.Forms.ColumnHeader Title;
        private System.Windows.Forms.ColumnHeader Login;
        private System.Windows.Forms.Label lblBasePathDesc;
        private System.Windows.Forms.Label lblAndroidPathDesc;
        private System.Windows.Forms.Label lblPsdConnectionDesc;
        private System.Windows.Forms.Label lblBasePath;
        private System.Windows.Forms.Label lblAndroidBasePath;
        private System.Windows.Forms.Label lblPsdComPort;
        private System.Windows.Forms.MenuStrip menuMain;
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
        private System.Windows.Forms.Button btnMoveDown;
        private System.Windows.Forms.Button btnMoveUp;
        private System.Windows.Forms.Button btnSaveAll;
        private System.Windows.Forms.Button btnRemovePass;
        private System.Windows.Forms.Button btnAddPass;
        private System.Windows.Forms.ToolStripMenuItem connectToolStripMenuItem;
        private System.Windows.Forms.Button btnUpdate;
    }
}

