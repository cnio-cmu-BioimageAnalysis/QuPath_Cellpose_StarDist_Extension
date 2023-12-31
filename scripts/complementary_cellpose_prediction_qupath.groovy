import qupath.ext.biop.cellpose.Cellpose2D

// Specify the model name (cyto, nuc, cyto2, omni_bact or a path to your custom model)
def pathModel = 'cyto'

def cellpose = Cellpose2D.builder( pathModel )
        .useGPU()
        .pixelSize( 0.5 )              // Resolution for detection
        .channels( 'DAPI' )            // Select detection channel(s)
//        .preprocess( ImageOps.Filters.median(1) )                // List of preprocessing ImageOps to run on the images before exporting them
        .tileSize(256)                // If your GPU can take it, make larger tiles to process fewer of them. Useful for Omnipose
//        .cellposeChannels(1,2)         // Overwrites the logic of this plugin with these two values. These will be sent directly to --chan and --chan2
//        .maskThreshold(-0.2)           // Threshold for the mask detection, defaults to 0.0
//        .flowThreshold(0.5)            // Threshold for the flows, defaults to 0.4 
//        .diameter(0)                   // Median object diameter. Set to 0.0 for the `bact_omni` model or for automatic computation
//        .invert()                      // Have cellpose invert the image
//        .useOmnipose()                 // Add the --omni flag to use the omnipose segmentation model
//        .excludeEdges()                // Clears objects toutching the edge of the image (Not of the QuPath ROI)
//        .clusterDBSCAN()               // Use DBSCAN clustering to avoir over-segmenting long object
        .cellExpansion(5.0)            // Approximate cells based upon nucleus expansion
//        .cellConstrainScale(1.5)       // Constrain cell expansion using nucleus size
//        .classify("My Detections")     // PathClass to give newly created objects
        .measureShape()                // Add shape measurements
        .measureIntensity()            // Add cell measurements (in all compartments)  
//        .createAnnotations()           // Make annotations instead of detections. This ignores cellExpansion
        .build()

// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("Cellpose", "Please select a parent object!")
    return
}
cellpose.overlap = 100
cellpose.detectObjects(imageData, pathObjects)
println 'Done!'