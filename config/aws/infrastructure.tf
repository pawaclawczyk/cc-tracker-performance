variable "aws_access_key" {}

variable "aws_secret_key" {}

variable "aws_region" {
  default = "eu-west-1"
}

variable "instance_type" {
  default = "c4.xlarge"
}

variable "ssh_user" {
  default = "ubuntu"
}

variable "ssh_public_key" {
  default = "~/.ssh/id_rsa.pub"
}

variable "ssh_private_key" {
  default = "~/.ssh/id_rsa"
}

provider "aws" {
  access_key = "${var.aws_access_key}"
  secret_key = "${var.aws_secret_key}"
  region     = "${var.aws_region}"
}

resource "aws_key_pair" "tracker_performance" {
  key_name   = "tracker_performance"
  public_key = "${file(var.ssh_public_key)}"
}

resource "aws_security_group" "tracker_performance" {
  name = "tracker_performance"

  ingress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    self = true
  }

  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

data "aws_ami" "tracker_performance" {
  most_recent = true

  name_regex = "^tracker-performance-\\d+"

  owners = ["self"]
}

resource "aws_instance" "tracker_performance" {
  ami           = "${data.aws_ami.tracker_performance.id}"
  instance_type = "${var.instance_type}"

  key_name      = "tracker_performance"

  security_groups = ["tracker", "tracker_performance"]

  tags {
    Name = "tracker_performance"
  }
}

resource "aws_eip" "tracker_performance_ip" {
  instance = "${aws_instance.tracker_performance.id}"
}

output "ip" {
  value = "${aws_eip.tracker_performance_ip.public_ip}"
}
