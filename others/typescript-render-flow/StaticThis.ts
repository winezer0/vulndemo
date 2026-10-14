class InstanceType { run(): void {} }
class StaticType { run(): void {} }
class Sides {
  instance: InstanceType;
  static shared: StaticType;
  static staticCall(): void { this.shared.run(); this.instance.run(); }
  instanceCall(): void { this.instance.run(); this.shared.run(); }
}
