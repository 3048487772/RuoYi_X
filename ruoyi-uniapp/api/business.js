import { upload } from '@/common/request'
//上传
export function commonUpload(path) {
  return upload({
    url: '/common/upload',
    path: path
  })
}
