import { IUser } from 'app/core/user/user.model';
import { IMember } from 'app/shared/model//member.model';

export interface IMember {
    id?: number;
    name?: string;
    iban?: string;
    mobile?: string;
    platformToken?: string;
    userToken?: string;
    bankAccountToken?: string;
    user?: IUser;
    member?: IMember;
    friends?: IMember[];
}

export class Member implements IMember {
    constructor(
        public id?: number,
        public name?: string,
        public iban?: string,
        public mobile?: string,
        public platformToken?: string,
        public userToken?: string,
        public bankAccountToken?: string,
        public user?: IUser,
        public member?: IMember,
        public friends?: IMember[]
    ) {}
}
