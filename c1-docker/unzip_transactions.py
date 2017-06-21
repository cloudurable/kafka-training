#!/root/anaconda3/bin/python

import zipfile 
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--source-file', help='(relative) path to source zip file')
parser.add_argument('--dest-dir', help='(relative) path to unzipped file(s)')
args = parser.parse_args()


def unzip_transactions(source_file, dest_dir):
    zip_ref = zipfile.ZipFile(source_file, 'r')
    zip_ref.extractall(dest_dir)
    zip_ref.close()


def main():
    if __name__ == "__main__":
        if len(args) == 2:
            unzip_transactions(args[0], args[1])
        else:
            print("Sorry, need to have two args, not {}".format(len(args)))

