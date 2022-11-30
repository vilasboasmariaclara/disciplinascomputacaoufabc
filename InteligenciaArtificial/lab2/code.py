from sklearn.datasets import load_iris

from sklearn.neighbors import KNeighborsClassifier

from sklearn.model_selection import train_test_split

from sklearn.metrics import classification_report

X, y = load_iris(return_X_y=True)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.30, random_state=13)

n_neighbors = 10

clf = KNeighborsClassifier(n_neighbors=n_neighbors)

clf.fit(X_train, y_train)

y_pred = clf.predict(X_test)

clf.score(X_test, y_test)

print(classification_report(y_test, y_pred, target_names=iris.target_name))