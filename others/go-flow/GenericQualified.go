package sample

import pkg "example.com/widgets"

func closeGenericWorker() {
	worker := pkg.Worker[string]{}
	worker.Close()
}
