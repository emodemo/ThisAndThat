from keras.datasets import imdb
from keras.preprocessing import sequence
from keras.models import Sequential
from keras.layers import Dense, Embedding, SimpleRNN

max_features = 10000  # number of words to consider as features
maxlen = 500  # cut texts after this number of words (among top max_features most common words)
batch_size = 32

(x_train, y_train), (x_test, y_test) = imdb.load_data(num_words=max_features)

# Reverse sequences to check if order matters, or shuffle
# x_train = [x[::-1] for x in x_train]
# x_test = [x[::-1] for x in x_test]

x_train = sequence.pad_sequences(x_train, maxlen=maxlen)
x_test = sequence.pad_sequences(x_test, maxlen=maxlen)

model = Sequential()
model.add(Embedding(max_features, 32))
model.add(SimpleRNN(32)) # or LSTM(32), or GRU(32, input_shape=(None, float_data.shape[-1])
# 1) RECURRENT DROPOUT model.add(SimpleRNN(32, dropout=0.2, recurrent_dropout=0.2))
# 2) STACKING SEVERAL LAYERS
# all intermediate layers should return their full sequence of outputs (a 3D tensor)
# rather than their output at the last timestep. This is done by specifying return_sequences=True.
# 3) BIDIRECTIONAL NN remove the SimpleRNN above, and add Bidirectional(SimpleRNN) below
# model.add(layers.Bidirectional(layers.SimpleRNN(32)))
model.add(Dense(1, activation='sigmoid'))

model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['acc'])
history = model.fit(x_train, y_train, epochs=10, batch_size=128, validation_split=0.2)
# the GRU version
# model.compile(optimizer=RMSprop(), loss='mae')
#history = model.fit_generator(train_gen, steps_per_epoch=500, epochs=20,
#                              validation_data=val_gen, validation_steps=val_steps)