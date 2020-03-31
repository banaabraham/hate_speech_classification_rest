# hate_speech_classification_rest
Multi-label hate-speech classification on text using tensorflow and deployed on spring rest.
deeplearning model was trained using dataset obtained from this paper (https://www.aclweb.org/anthology/W19-3506/) using tensorflow 1.14. The trained model can be donwloaded at https://testdeveloper.s3.amazonaws.com/mlp.h5 and place it under /target/data.
The model obtained 10 fold cross-validation accuracy of 0.897 using the same metrics calculation as the underlying paper.
The request can be made using GET request using this JSON format { "textInput":"something" }
