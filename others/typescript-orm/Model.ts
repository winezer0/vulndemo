import { Entity as Model } from "typeorm";

@Model("users")
export class User {}

@Model({"name": "quoted_accounts"})
export class Account {}

declare const options: {name: string};

@Model({name: "unconfirmed", ...options})
export class DynamicAccount {}

@Model({...options, name: "ordered_accounts"})
export class OrderedAccount {}

@Model("users\\u005farchive")
export class EscapedAccount {}

import { Entity } from "./local";

@Entity("fake")
export class Fake {}

{
  const Model = (_name: string) => (_target: unknown) => {};
  @Model("shadowed") class Shadowed {}
}
