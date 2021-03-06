from keras.datasets import imdb
from keras.preprocessing import sequence

max_features = 10000  # number of words to consider as features
max_len = 500  # cut texts after this number of words (among top max_features most common words)

print('Loading data...')
(x_train, y_train), (x_test, y_test) = imdb.load_data(num_words=max_features)
print(len(x_train), 'train sequences')
print(len(x_test), 'test sequences')

print('Pad sequences (samples x time)')
x_train = sequence.pad_sequences(x_train, maxlen=max_len)
x_test = sequence.pad_sequences(x_test, maxlen=max_len)
print('x_train shape:', x_train.shape)
print('x_test shape:', x_test.shape)

# !!! Because 1D convnets process input patches independently,
# they aren’t sensitive to the order of the timesteps
# (beyond a local scale, the size of the convolution windows), unlike RNNs.

from keras.models import Sequential
from keras import layers
from keras.optimizers import RMSprop

model = Sequential()
model.add(layers.Embedding(max_features, 128, input_length=max_len))
model.add(layers.Conv1D(32, 7, activation='relu'))
model.add(layers.MaxPooling1D(5))
model.add(layers.Conv1D(32, 7, activation='relu'))
# CONVOLUTION AND RNN TOGETHER
# Because RNNs are extremely expensive for processing very long sequences,
# but 1D convnets are cheap, it can be a good idea to use a 1D convnet as a preprocessing step before an RNN,
# shortening the sequence and extracting useful representations for the RNN to process.
# model.add(layers.GRU(32, dropout=0.1, recurrent_dropout=0.5))
# model.add(layers.Dense(1))
model.add(layers.GlobalMaxPooling1D())
model.add(layers.Dense(1))

model.summary()

model.compile(optimizer=RMSprop(lr=1e-4),
              loss='binary_crossentropy',
              metrics=['acc'])
history = model.fit(x_train, y_train,
                    epochs=10,
                    batch_size=128,
                    validation_split=0.2)