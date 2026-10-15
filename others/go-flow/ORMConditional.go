package sample

type ORMConditionalUser struct {
	Name string `gorm:"column:name"`
}

func (ORMConditionalUser) TableName() string {
	if enabled {
		return "active_users"
	}
	return "orm_conditional_users"
}
