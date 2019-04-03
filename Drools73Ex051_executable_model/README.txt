To generate execution model, run with property generateModel=yes.

$ mvn clean install -DgenerateModel=yes

You can confirm it by looking inside the created kjar.


~~~
YES:     Generates the executable model corresponding to the DRL files in the original project and excludes the DRL files from the generated KJAR.
WITHDRL: Generates the executable model corresponding to the DRL files in the original project and also adds the DRL files to the generated KJAR for documentation purposes (the KIE base is built from the executable model regardless).
NO:      Does not generate the executable model. 
~~~
