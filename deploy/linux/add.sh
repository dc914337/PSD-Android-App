rm phone.psd
rm passes.psd
mono ./psd/psd/bin/Debug/psd.exe reset --userpassword root --usepsd --use-first-found-psd
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --enter-with-login --userpassword root --title "VK" --login "+79219245625" --password "i really looo0000ve big passes with spaces" --description "Pass for the main VK account" -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --userpassword root --title "GitHub" --login "paranoic3212@gmail.com" --password "one big pass" --description "Pass for my github" -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --enter-with-login --userpassword root --title "Google" --login "paranoic3212@gmail.com" --password "another big pass" -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --userpassword root --title "Windows Work" --password "i really looove big passes with spaces" --description "Work windows account." -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --userpassword root --title "Satan" --password "AVE SATAN! Satan is the best. In satan we trust" --description "I LOOOVE SATAN." -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --userpassword root --title "Ctulhu" --password "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn" --description "Yay! Lovecraft" -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --enter-with-login --userpassword root --title "Windows Home" --login myname --password "i really love big passes with spaces" --description "Home windows account" -v
mono ./psd/psd/bin/Debug/psd.exe add -b "passes.psd" --usepsd -p "phone.psd" --userpassword root --title "Longest pass" --login "Yay!" --password "Ave Satani! Ave Satani! Ave Satani! Ave Satani! Ave Satani! Ave Satani! Ave Satani! Ave Satani! Ave Satani! Yay Satan! Yay Satan" --description "Longest available password" -v
echo --------------------------------[ LISTING ]------------------------------------
mono ./psd/psd/bin/Debug/psd.exe list -b passes.psd --userpassword root -v 