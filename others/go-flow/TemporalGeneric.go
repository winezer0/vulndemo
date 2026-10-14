package sample

type GenericWorker[T any] struct{}

func registerGenericActivities(registry interface{ RegisterActivities(interface{}) }) {
	registry.RegisterActivities(&GenericWorker[string]{})
}
