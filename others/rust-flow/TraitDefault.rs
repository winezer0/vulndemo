trait Runner {
    fn run<U>(&self, value: U) -> U { value }
}

struct Job;

impl Runner for Job {}
