module example.invalid/replacedblock

go 1.23

replace (
	example.invalid/original => example.invalid/fork v1.0.0
)
