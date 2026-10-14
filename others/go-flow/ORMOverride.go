package sample

type ORMOverrideUser struct {
	Name string `gorm:"column:name"`
}

func (u ORMOverrideUser) TableName() string {
	return "custom_users"
}
