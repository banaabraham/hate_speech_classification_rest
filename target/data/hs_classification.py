

import numpy as np
import pandas as pd
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from keras.models import load_model
import os
import argparse
import json
import pathlib

path = str(pathlib.Path().absolute())

os.environ['CUDA_VISIBLE_DEVICES'] = '-1'

parser = argparse.ArgumentParser(description='Hate-speech classifier')
parser.add_argument('input', metavar='S', type=str, nargs='+',
                   help='Input String')

print("------BEGIN------")

max_words = 15000
max_len = 100

args = parser.parse_args()
text_input = args.input[0]


df = pd.read_csv(path+"\\data\\re_dataset.csv",
                 encoding = "ISO-8859-1")

text = df["Tweet"]
label = np.array(df.iloc[:,1:])

tokenizer = Tokenizer(num_words=max_words,oov_token="<SIAPKH>",)
tokenizer.fit_on_texts(text)
sequences = tokenizer.texts_to_sequences(text)
data = pad_sequences(sequences,maxlen=max_len)

word_index = tokenizer.word_index


model = load_model(path+"\\data\\mlp.h5")

seq_masukan = tokenizer.texts_to_sequences([text_input])
masukan_data = pad_sequences(seq_masukan,maxlen=max_len)
pred = model.predict(masukan_data)

columns =  df.columns

f = open(path+"\\data\\output.json","w")

result_dict = dict()

for i in range(1,12):
  result_dict[columns[i].lower()] = str(pred[0,i])

print(result_dict)
f.write(json.dumps(result_dict))
f.close()
  
