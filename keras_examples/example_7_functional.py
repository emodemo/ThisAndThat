from keras import Sequential, Model, Input
from keras.layers import Dense, Embedding, LSTM, Conv1D, Conv2D, MaxPool1D, GlobalMaxPool1D, AveragePooling2D, \
    concatenate, add

# 0) Hyper parameter Optimization
# Keras + Hyperopt => https://github.com/maxpumperla/hyperas

# 1) - Sequential
seq_model = Sequential()
seq_model.add(Dense(32, activation='relu', input_shape=(64,)))
seq_model.add(Dense(32, activation='relu'))
seq_model.add(Dense(10, activation='softmax'))

# 2) - same but Functional
input_tensor = Input(shape=(64,))
output_tensor = Dense(32, activation='relu')(input_tensor)
output_tensor = Dense(32, activation='relu')(output_tensor)
output_tensor = Dense(10, activation='softmax')(output_tensor)
model = Model(input_tensor, output_tensor) # create model out of the tensors

# 3) two-input concatenation
input_1_text = Input(shape=(None,), dtype='int32', name='text')
embedded_text = Embedding(64, 10000)(input_1_text) # embed in vector of sie 64
embedded_text = LSTM(32)(embedded_text) # encode in LSTM
input_2_question = Input(shape=(None,), dtype='int32', name='question')
embedded_question = Embedding(32, 10000)(input_2_question) # embed in vector of sie 32
embedded_question = LSTM(16)(embedded_question) # encode in LSTM
# concatenate the two inputs
concatenated = concatenate([embedded_text, embedded_question], axis=-1)
# add a softmax classifier on top
answer = Dense(500, activation='softmax')(concatenated)
# create the model
model_2in_1out = Model([input_1_text, input_2_question], answer)
model_2in_1out.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['acc'])
#model_2in_1out.fit([text, qusetion], answer, epochs=10, batch_size=128)
#model_2in_1out.fit({'text': text, 'question': question}, answer, epochs=10, batch_size=128) #only if inputs are names

# 4) three-output with different loss functions
# but very imbalanced loss contributions will cause the model representations to be optimized for the task with
# the task with the largest individual loss => use levels of importance to the loss functions 'loss_weights{}'
input_text = Input(shape=(None,), dtype='int32', name='text')
embedded_text = Embedding(64, 10000)(input_1_text) # embed in vector of sie 64
output = Conv1D(128, 5, activation='relu')(embedded_text)
output = MaxPool1D(5)(output)
output = Conv1D(256, 5, activation='relu')(output)
output = GlobalMaxPool1D()(output)
output = Dense(128, activation='relu')(output)
# output layers must have names
out_1_age_prediction = Dense(1, name='age')(output)
out_2_income_prediction = Dense(10, activation='softmax', name='income')(output)
out_3_sex_prediction = Dense(1, activation='sigmoid', name='sex')(output)
model_1in_3out = Model(input_text, [out_1_age_prediction, out_2_income_prediction, out_3_sex_prediction])
# several ways to compile
model_1in_3out.compile(optimizer='rmsprop', loss=['mse', 'categorical_crossentropy', 'binary_crossentropy'])
#model_1in_3out.compile(optimizer='rmsprop', loss={'age':'mse', ... })
#model_1in_3out.compile(optimizer='rmsprop', loss=['mse', ...], loss_weights=[0.25, 1., 10.])
# several ways to train
#model_1in_3out.fit(input_text, [age_targets, income_targets, sex_targets], epochs=10, batch_size=128)
#model_1in_3out.fit(input_text, {'age': age_targets, ..}

# 5) Acyclic Directed Graph. Inception module example.
# There are ready made applications.inception_v3.InceptionV3 and Xception application for ImageNet
x = [] # the input
branch_a = Conv2D(128, 1, activation='relu', strides=2) (x)
branch_b = Conv2D(128, 1, activation='relu') (x)
branch_b = Conv2D(128, 3, activation='relu', strides=2) (branch_b)
branch_c = AveragePooling2D(3, strides=2) (x)
branch_c = Conv2D(128, 3, activation='relu') (branch_c)
branch_d = Conv2D(128, 1, activation='relu') (x)
branch_d = Conv2D(128, 3, activation='relu') (branch_d)
branch_d = Conv2D(128, 3, activation='relu', strides=2) (branch_d)
output_inception = concatenate([branch_a, branch_b, branch_c, branch_d], axis=-1)

# 6) Residuals: in large-scale models tackles
# a) vanishing gradient
# b) representational bottleneck: one layer can access only what is in previous' activations
# impl: make the output of earlier layer as input to a later layer, rather than concatenating them
earlier = []
earlier = Conv2D(128, 3, activation='relu', padding='same')(earlier)
later = Conv2D(128, 3, activation='relu', padding='same')(earlier)
later = Conv2D(128, 3, activation='relu', padding='same')(later)
later = add([earlier, later]) # add the original earlier back to the output features

# 7) layers.BatchNormalization, after a ConV or Dense layer. Normalizes data (mean, sd).
# 8) layers.SeparableConv2, depthwise separable convolution, faster/better than Conv2D. Use this one!
# 9) Model Ensembling: pool together the predictions of several models. model diversity is good!
prediction_a = model_a.predict(x)
prediction_b = model_b.predict(x)
prediction_c = model_c.predict(x)
prediction_d = model_d.predict(x)
# (0.5, 0.25, 0.1, 0.15) are learned empirically :-)
prediction_all = 0.5 * prediction_a + 0.25 * prediction_b + 0.1 * prediction_c + 0.15 * prediction_d

# 10) General notes: last layer to be
# Binary classification: Dense(1, activation=sigmoid, loss=binary_crossentropy)
# Single label: Dense(x, activation=softmax, loss=(sparse_)/categorical_crossentropy)# for (int)/or one-hot encoding
# Multi label: Dense(x, activation=sigmoid, loss=binary_crossentropy)# k-hot target encoding
# Regression: Dense(NofPredictedValues, activation=null, loss=mse/mea)# NofPredictedValues can be 1 (as in house price)