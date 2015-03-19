function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
m = size(X, 1);
num_labels = size(Theta2, 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Complete the following code to make predictions using
%               your learned neural network. You should set p to a 
%               vector containing labels between 1 to num_labels.
%
% Hint: The max function might come in useful. In particular, the max
%       function can also return the index of the max element, for more
%       information see 'help max'. If your examples are in rows, then, you
%       can use max(A, [], 2) to obtain the max for each row.
%
X = [ones(m, 1) X];

Z=sigmoid(X* (Theta1')); 

Z = [ones(m, 1) Z];

H=sigmoid(Z* (Theta2')); 

[values, indexes] = max(H,[], 2);

p=indexes;


%Hidden=Theta1*X';
%a2=sigmoid(Hidden);

%a2=a2';
%a2 = [ones(size(a2)(1), 1) a2];

%Output=Theta2*a2';
%p=sigmoid(Output);
%p=p';

%for i=1:size(p)(1)
%temp=p(i, :);

%[val,ind]=max(temp);
%p(i)=ind;
%endfor





% =========================================================================


end
