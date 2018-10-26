* Checkout the source code
git clone git@github.com:sharmalab/MEDIator.git

* Download a set of medical source from The Cancer Genome Atlas (TCGA)
https://tcga-data.nci.nih.gov/tcga/

When you pick the data and click download, it will send the files in a tar, along with a file map, "FILE_SAMPLE_MAP.txt".
Download the tar, extract them, and upload to S3.
NOTE: The tar contains the associated “blob” that will be linked with the clinical data. The blob in itself does not have any metadata.

* Meta data
Place all the meta data files in the root folder of the data replication system source code.

* Constants
edu.emory.bmi.mediator.constants package contains the constants to be modified.
The class DataSourcesConstants contains the constants.

Set the name of the CSV file that is used to load the CSV meta data into Infinispan.
    public static final String META_CSV_FILE = "getAllDataAsCSV";
NOTE: This is the clinical data

Also set the name of the file sample map of TCGA that is uploaded to S3. 
    public static final String S3_META_CSV_FILE = "FILE_SAMPLE_MAP.txt";
NOTE: This is the mapping between the clinical data and the associated “blob” that was uploaded to S3


* S3 constants
S3 base url contains https://s3.amazonaws.com/ and the bucket name, (dataReplServer was chosen for this prototype).
    public static final String S3_BASE_URL = "https://s3.amazonaws.com/dataReplServer/";

Folder names.
Moreover, the meta data comes into multiple folders, with level1 as the default. If you want to change the default folder, change the value of the constant S3_LEVEL1. 
    public static final String S3_LEVEL1 = "level1";
    public static final String S3_LEVEL2 = "level2";
    public static final String S3_LEVEL3 = "level3";

* API Key
Provide your TCIA API Key in TCIAConstants for the below field.
    public static String API_KEY = "";
NOTE: This is the TCIA API Key

* CA constants.
Check the caMicroscope base url and sample query url that is used in the prototype, in DataSourcesConstants class.
    public static final String CA_MICROSCOPE_BASE_URL = "http://imaging.cci.emory.edu/camicroscope/camicroscope/";
    public static final String CA_MICROSCOPE_QUERY_URL = "osdCamicroscope.php?tissueId=";

* Build from the source code root:
$ mvn package

* To execute the DSIntegratorIntegrator:
$ java -classpath lib/core-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.mediator.ds_impl.DSIntegratorInitiator

* Access the Data Replication API from a REST Client. See API documentation for more details. 
