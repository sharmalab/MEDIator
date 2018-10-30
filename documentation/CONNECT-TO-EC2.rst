## Connect to An EC2 Instance
If you prefer to use an EC2 Instance to upload clinical files to S3, you may follow the below steps.

Otherwise, you may directly upload the clinical files which were downloaded to your local computer.

* Connect to the instance


     ssh -i pradeeban.pem ubuntu@ec2-54-237-35-248.compute-1.amazonaws.com

* Make a directory and change to the directory.


     mkdir gsoc2014

     cd gsoc2014

* Download and extract the meta data from the online repository (link sent to email. Given below is an example).


     nohup wget https://tcga-data.nci.nih.gov/tcgafiles/ftp_auth/distro_ftpusers/anonymous/userCreatedArchives/652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar > nohup.out &

     tar -zxvf 652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar

