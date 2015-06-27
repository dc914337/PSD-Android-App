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
            this.lblPsdConnected = new System.Windows.Forms.Label();
            this.menuMain = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAllMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.exitMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.pCToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newPCBaseMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openPcMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.savePcMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsPcMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.phoneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newPhoneMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openPhoneMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.savePhoneMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsPhoneMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.deviceToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.resetPsdMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToPsdMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.writeFirmwarePsdMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.connectPsdMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutMenuItem = new System.Windows.Forms.ToolStripMenuItem();
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
            // 
            // lblAndroidPathDesc
            // 
            this.lblAndroidPathDesc.AutoSize = true;
            this.lblAndroidPathDesc.Location = new System.Drawing.Point(4, 465);
            this.lblAndroidPathDesc.Name = "lblAndroidPathDesc";
            this.lblAndroidPathDesc.Size = new System.Drawing.Size(73, 13);
            this.lblAndroidPathDesc.TabIndex = 21;
            this.lblAndroidPathDesc.Text = "Android path: ";
            // 
            // lblPsdConnectionDesc
            // 
            this.lblPsdConnectionDesc.AutoSize = true;
            this.lblPsdConnectionDesc.Location = new System.Drawing.Point(4, 481);
            this.lblPsdConnectionDesc.Name = "lblPsdConnectionDesc";
            this.lblPsdConnectionDesc.Size = new System.Drawing.Size(90, 13);
            this.lblPsdConnectionDesc.TabIndex = 22;
            this.lblPsdConnectionDesc.Text = "Connected PSD: ";
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
            // lblPsdConnected
            // 
            this.lblPsdConnected.AutoSize = true;
            this.lblPsdConnected.Location = new System.Drawing.Point(100, 481);
            this.lblPsdConnected.Name = "lblPsdConnected";
            this.lblPsdConnected.Size = new System.Drawing.Size(0, 13);
            this.lblPsdConnected.TabIndex = 25;
            // 
            // menuMain
            // 
            this.menuMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.pCToolStripMenuItem,
            this.phoneToolStripMenuItem,
            this.deviceToolStripMenuItem,
            this.aboutMenuItem});
            this.menuMain.Location = new System.Drawing.Point(0, 0);
            this.menuMain.Name = "menuMain";
            this.menuMain.Size = new System.Drawing.Size(416, 24);
            this.menuMain.TabIndex = 30;
            this.menuMain.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.saveAllMenu,
            this.exitMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // saveAllMenu
            // 
            this.saveAllMenu.Name = "saveAllMenu";
            this.saveAllMenu.Size = new System.Drawing.Size(152, 22);
            this.saveAllMenu.Text = "Save all";
            this.saveAllMenu.Click += new System.EventHandler(this.saveAllToolStripMenuItem_Click);
            // 
            // exitMenuItem
            // 
            this.exitMenuItem.Name = "exitMenuItem";
            this.exitMenuItem.Size = new System.Drawing.Size(152, 22);
            this.exitMenuItem.Text = "Exit";
            this.exitMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
            // 
            // pCToolStripMenuItem
            // 
            this.pCToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newPCBaseMenuItem,
            this.openPcMenuItem,
            this.savePcMenuItem,
            this.saveAsPcMenuItem});
            this.pCToolStripMenuItem.Name = "pCToolStripMenuItem";
            this.pCToolStripMenuItem.Size = new System.Drawing.Size(34, 20);
            this.pCToolStripMenuItem.Text = "PC";
            // 
            // newPCBaseMenuItem
            // 
            this.newPCBaseMenuItem.Name = "newPCBaseMenuItem";
            this.newPCBaseMenuItem.Size = new System.Drawing.Size(152, 22);
            this.newPCBaseMenuItem.Text = "New";
            this.newPCBaseMenuItem.Click += new System.EventHandler(this.newPCBaseMenuItem_Click);
            // 
            // openPcMenuItem
            // 
            this.openPcMenuItem.Name = "openPcMenuItem";
            this.openPcMenuItem.Size = new System.Drawing.Size(152, 22);
            this.openPcMenuItem.Text = "Open";
            // 
            // savePcMenuItem
            // 
            this.savePcMenuItem.Name = "savePcMenuItem";
            this.savePcMenuItem.Size = new System.Drawing.Size(152, 22);
            this.savePcMenuItem.Text = "Save";
            this.savePcMenuItem.Click += new System.EventHandler(this.savePcMenuItem_Click);
            // 
            // saveAsPcMenuItem
            // 
            this.saveAsPcMenuItem.Name = "saveAsPcMenuItem";
            this.saveAsPcMenuItem.Size = new System.Drawing.Size(152, 22);
            this.saveAsPcMenuItem.Text = "Save As";
            this.saveAsPcMenuItem.Click += new System.EventHandler(this.saveAsPcMenuItem_Click);
            // 
            // phoneToolStripMenuItem
            // 
            this.phoneToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newPhoneMenuItem,
            this.openPhoneMenuItem,
            this.savePhoneMenuItem,
            this.saveAsPhoneMenuItem});
            this.phoneToolStripMenuItem.Name = "phoneToolStripMenuItem";
            this.phoneToolStripMenuItem.Size = new System.Drawing.Size(53, 20);
            this.phoneToolStripMenuItem.Text = "Phone";
            // 
            // newPhoneMenuItem
            // 
            this.newPhoneMenuItem.Name = "newPhoneMenuItem";
            this.newPhoneMenuItem.Size = new System.Drawing.Size(152, 22);
            this.newPhoneMenuItem.Text = "New";
            // 
            // openPhoneMenuItem
            // 
            this.openPhoneMenuItem.Name = "openPhoneMenuItem";
            this.openPhoneMenuItem.Size = new System.Drawing.Size(152, 22);
            this.openPhoneMenuItem.Text = "Open";
            // 
            // savePhoneMenuItem
            // 
            this.savePhoneMenuItem.Name = "savePhoneMenuItem";
            this.savePhoneMenuItem.Size = new System.Drawing.Size(152, 22);
            this.savePhoneMenuItem.Text = "Save";
            this.savePhoneMenuItem.Click += new System.EventHandler(this.savePhoneMenuItem_Click);
            // 
            // saveAsPhoneMenuItem
            // 
            this.saveAsPhoneMenuItem.Name = "saveAsPhoneMenuItem";
            this.saveAsPhoneMenuItem.Size = new System.Drawing.Size(152, 22);
            this.saveAsPhoneMenuItem.Text = "Save As";
            this.saveAsPhoneMenuItem.Click += new System.EventHandler(this.saveAsPhoneMenuItem_Click);
            // 
            // deviceToolStripMenuItem
            // 
            this.deviceToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.resetPsdMenuItem,
            this.saveToPsdMenuItem,
            this.writeFirmwarePsdMenuItem,
            this.connectPsdMenuItem});
            this.deviceToolStripMenuItem.Name = "deviceToolStripMenuItem";
            this.deviceToolStripMenuItem.Size = new System.Drawing.Size(54, 20);
            this.deviceToolStripMenuItem.Text = "Device";
            // 
            // resetPsdMenuItem
            // 
            this.resetPsdMenuItem.Name = "resetPsdMenuItem";
            this.resetPsdMenuItem.Size = new System.Drawing.Size(152, 22);
            this.resetPsdMenuItem.Text = "Reset PSD";
            // 
            // saveToPsdMenuItem
            // 
            this.saveToPsdMenuItem.Name = "saveToPsdMenuItem";
            this.saveToPsdMenuItem.Size = new System.Drawing.Size(152, 22);
            this.saveToPsdMenuItem.Text = "Save to PSD";
            this.saveToPsdMenuItem.Click += new System.EventHandler(this.saveToPsdMenuItem_Click);
            // 
            // writeFirmwarePsdMenuItem
            // 
            this.writeFirmwarePsdMenuItem.Name = "writeFirmwarePsdMenuItem";
            this.writeFirmwarePsdMenuItem.Size = new System.Drawing.Size(152, 22);
            this.writeFirmwarePsdMenuItem.Text = "Write firmware";
            // 
            // connectPsdMenuItem
            // 
            this.connectPsdMenuItem.Name = "connectPsdMenuItem";
            this.connectPsdMenuItem.Size = new System.Drawing.Size(152, 22);
            this.connectPsdMenuItem.Text = "Connect";
            // 
            // aboutMenuItem
            // 
            this.aboutMenuItem.Name = "aboutMenuItem";
            this.aboutMenuItem.Size = new System.Drawing.Size(52, 20);
            this.aboutMenuItem.Text = "About";
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
            this.Controls.Add(this.lblPsdConnected);
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
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.PSDForm_FormClosing);
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
        private System.Windows.Forms.Label lblPsdConnected;
        private System.Windows.Forms.MenuStrip menuMain;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAllMenu;
        private System.Windows.Forms.ToolStripMenuItem exitMenuItem;
        private System.Windows.Forms.ToolStripMenuItem pCToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newPCBaseMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openPcMenuItem;
        private System.Windows.Forms.ToolStripMenuItem phoneToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem deviceToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutMenuItem;
        private System.Windows.Forms.ToolStripMenuItem savePcMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAsPcMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newPhoneMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openPhoneMenuItem;
        private System.Windows.Forms.ToolStripMenuItem savePhoneMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAsPhoneMenuItem;
        private System.Windows.Forms.ToolStripMenuItem resetPsdMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToPsdMenuItem;
        private System.Windows.Forms.ToolStripMenuItem writeFirmwarePsdMenuItem;
        private System.Windows.Forms.Button btnMoveDown;
        private System.Windows.Forms.Button btnMoveUp;
        private System.Windows.Forms.Button btnSaveAll;
        private System.Windows.Forms.Button btnRemovePass;
        private System.Windows.Forms.Button btnAddPass;
        private System.Windows.Forms.ToolStripMenuItem connectPsdMenuItem;
        private System.Windows.Forms.Button btnUpdate;
    }
}

