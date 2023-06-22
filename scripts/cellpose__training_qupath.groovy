import qupath.ext.biop.cellpose.Cellpose2D

def cellpose = Cellpose2D.builder("cyto2") // Can choose "None" if you want to train from scratch
                  .cellposeChannels( 0, 0)
//                .channels("1", "3")  // or use work with .cellposeChannels( channel1, channel2 ) and follow the cellpose way
//                .preprocess(ImageOps.Filters.gaussianBlur(1)) // Optional preprocessing QuPath Ops
//                .epochs(500)             // Optional: will default to 500
//                .learningRate(0.2)       // Optional: Will default to 0.2
//                .batchSize(8)            // Optional: Will default to 8
//                .minTrainMasks(5)        // Optional: Will default to 5
//                .addParameter("save_flows")      // Any parameter from cellpose not available in the builder. See https://cellpose.readthedocs.io/en/latest/command.html
//                .addParameter("anisotropy", "3") // Any parameter from cellpose not available in the builder. See https://cellpose.readthedocs.io/en/latest/command.html
//                .modelDirectory( new File("C:/Users/acayuela/Desktop/test_cellpose/cellpose_models")) // Optional place to store resulting model. Will default to QuPath project root, and make a 'models' folder
//                .saveBuilder("My Builder") // Optional: Will save a builder json file that can be reloaded with Cellpose2D.builder(File builderFile)
                .build()

// Once ready for training you can call the train() method
// train() will:
// 1. Go through the current project and save all "Training" and "Validation" regions into a temp folder (inside the current project)
// 2. Run the cellpose training via command line
// 3. Recover the model file after training, and copy it to where you defined in the builder, returning the reference to it
// 4. If it detects the run-cellpose-qc.py file in your QuPath Extensions Folder, it will run validation for this model

def resultModel = cellpose.train()

// Pick up results to see how the training was performed
println "Model Saved under "
println resultModel.getAbsolutePath().toString()

// You can get a ResultsTable of the training. 
def results = cellpose.getTrainingResults()
results.show("Training Results")

// You can get a results table with the QC results to visualize 
def qcResults = cellpose.getQCResults()
qcResults.show("QC Results")


// Finally you have access to a very simple graph 
cellpose.showTrainingGraph()