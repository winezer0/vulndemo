package sample

func PointerActivity() {}

func registerPointerActivity(worker interface{ RegisterActivity(interface{}) }) {
	worker.RegisterActivity(&PointerActivity)
}
